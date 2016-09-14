//
//  PointsTabTwoViewController.swift
//  BeakonIOS
//
//  Created by Alejandra on 8/26/16.
//  Copyright © 2016 CentauroSolutions. All rights reserved.
//

import UIKit
import Alamofire
import SwiftyJSON
import JLToast

class PointsTabTwoViewController: UIViewController {
    @IBOutlet weak var mesageToSend: UITextField!
    @IBOutlet weak var messageL: UILabel!
    @IBOutlet weak var pointsL: UITextField!
    @IBOutlet weak var butonAction: UIButton!
    var pointsMinium = 0
    var userId = ""
    var code = ""
    var pointsUser = 2
    var message = ""
    
    override func viewDidLoad() {
        super.viewDidLoad()
        obtainMiniumPoints()
        butonAction.addTarget(self, action: #selector(PointsTabTwoViewController.service), forControlEvents: .TouchUpInside)
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    

    func chargeData(){
        let defaults = NSUserDefaults.standardUserDefaults()
        self.userId = defaults.objectForKey("userId") as! String
        self.pointsUser = defaults.objectForKey("points") as! Int
        messageL.text = "Usted tiene un total de " + String(self.pointsUser) + " pts disponibles para regalar, esta es la cantidad minima de puntos que puedes regalar: " + String(self.pointsMinium)
    }
    
    func obtainMiniumPoints(){
        //Endpoint
        let url : String = "http://butilsdevel.cfapps.io/utils/pointsMin"
        //Crea el request
        Alamofire.request(.GET, url, encoding: .JSON)
            .responseJSON
            {
                response in switch response.result
                {
                //Si la respuesta es satisfactoria
                case .Success(let JSON):
                    let response = JSON as! NSDictionary
                    //Si la respuesta no tiene status 404
                    if((response)["status"] as! Int != 404)
                    {
                        self.pointsMinium = response.objectForKey("minPoints") as! Int
                        self.chargeData()
                        
                    }
                    else
                    {
                        print("Hubo un error obteniendo los puntos minimos")
                    }
                case .Failure(let error):
                    print("Hubo un error realizando la peticion: \(error)")
                    JLToast.makeText("Hubo un error realizando la petición").show()
                }
        }
    }
    
    func serviceUpdateUserDefault()
    {
        let url : String = "http://buserdevel.cfapps.io/user/id/"+self.userId
        self.message = self.mesageToSend.text!
        Alamofire.request(.GET, url, encoding: .JSON).responseJSON
            {
                response in switch response.result
                {
                //Si la respuesta es satisfactoria
                case .Success(let JSON):
                    let response = JSON as! NSDictionary
                    var user = JSON as! NSDictionary
                    //Si la respuesta no tiene status 404
                    if((response)["status"] as! Int != 404)
                    {   user = response.objectForKey("user")! as! NSDictionary
                        let defaults = NSUserDefaults.standardUserDefaults()
                        defaults.setObject((user)["totalGiftPoints"] as! Int, forKey: "points")
                        self.chargeData()
                    }
                    else
                    {
                        JLToast.makeText("Hubo un error refrescando los puntos").show()
                    }
                case .Failure(let error):
                    print("Hubo un error realizando la peticion: \(error)")
                    JLToast.makeText("Hubo un error realizando la petición").show()
                }
        }
    }
    
    func service()
    {
        let points:Int? = Int(self.pointsL.text!)
        let url : String = "http://butilsdevel.cfapps.io/utils/exchangePoints"
        //Crea el request
        let newTodo : [String : AnyObject] =
            [
                "userId": self.userId,
                "points": points!
            ]
        if(self.pointsUser >= points)
        {
            Alamofire.request(.POST, url, parameters: newTodo , encoding: .JSON).responseJSON
            {
                response in switch response.result
                {
                //Si la respuesta es satisfactoria
                case .Success(let JSON):
                    let response = JSON as! NSDictionary
                    var pointsObject = JSON as! NSDictionary
                    //Si la respuesta no tiene status 404
                    if((response)["status"] as! Int != 404)
                    {
                        if((response)["message"] as! String == "Saldo flotante creado")
                        {
                            pointsObject = response.objectForKey("points")! as! NSDictionary
                            self.code = (pointsObject)["code"] as! String
                            //self.expirationDate = (pointsObject)["expirationDate"] as Float
                            JLToast.makeText("Creado con exito").show()
                            self.serviceUpdateUserDefault()
                            let secondViewController = self.storyboard?.instantiateViewControllerWithIdentifier("DetailGiftPointsViewController") as! DetailGiftPointsViewController
                            secondViewController.code = self.code
                            secondViewController.message = self.message
                            self.navigationController?.pushViewController(secondViewController, animated: true)

                        }
                        else if((response)["message"] as! String == "La cantidad de puntos no supera el minimo requerido")
                        {
                             JLToast.makeText("La cantidad de puntos no supera el minimo requerido").show()
                            
                        }
                    }
                    else
                    {
                        JLToast.makeText("Hubo un error solicitando regalar los puntos").show()
                    }
                case .Failure(let error):
                    print("Hubo un error realizando la peticion: \(error)")
                    JLToast.makeText("Hubo un error realizando la petición").show()
                }
             }
         }
        else
        {
            JLToast.makeText("No tiene los suficientes puntos para regalar esa cantidad").show()

        }
        
    }
    


}
