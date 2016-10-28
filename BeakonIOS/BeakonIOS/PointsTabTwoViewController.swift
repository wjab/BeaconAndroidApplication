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
    var dateExpiration:Int = 14
    var dateSend = ""
    
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
        NSNotificationCenter.defaultCenter().postNotificationName("refreshPoints", object: nil)
        NSNotificationCenter.defaultCenter().postNotificationName("refreshPointsHome", object: nil)
        
        messageL.text = Constants.messages.availablePointsMessage(String(self.pointsUser), minimum: String(self.pointsMinium))
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
                    JLToast.makeText(Constants.error_messages.call_to_ws_toast).show()
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
                    {
                        user = response.objectForKey("user")! as! NSDictionary
                        let defaults = NSUserDefaults.standardUserDefaults()
                        defaults.setObject((user)["totalGiftPoints"] as! Int, forKey: "points")
                        self.chargeData()
                    }
                    else
                    {
                        JLToast.makeText(Constants.error_messages.refreshing_points_error).show()
                    }
                case .Failure(let error):
                    print("Hubo un error realizando la peticion: \(error)")
                    JLToast.makeText(Constants.error_messages.call_to_ws_toast).show()
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
                            self.dateExpiration = (pointsObject)["expirationDate"] as! Int
                            if let theDate = NSDate(jsonDate: "/Date("+String(self.dateExpiration)+"-0800)/")
                            {
                                self.dateSend = String(theDate)
                                print(theDate)
                            }
                            else
                            {
                                print("wrong format")
                            }
                            JLToast.makeText(Constants.messages.created_success_toast).show()
                            self.pointsL.text = ""
                            self.mesageToSend.text = ""
                            self.serviceUpdateUserDefault()
                            let secondViewController = self.storyboard?.instantiateViewControllerWithIdentifier("DetailGiftPointsViewController") as! DetailGiftPointsViewController
                            secondViewController.code = self.code
                            secondViewController.message = self.message
                            secondViewController.expiration = self.dateSend
                           
                            self.navigationController?.pushViewController(secondViewController, animated: true)

                        }
                        else if((response)["message"] as! String == "La cantidad de puntos no supera el minimo requerido")
                        {
                             JLToast.makeText(String((response)["message"])).show()
                            
                        }
                    }
                    else
                    {
                        JLToast.makeText(Constants.error_messages.error_request_give_points).show()
                    }
                case .Failure(let error):
                    print("Hubo un error realizando la peticion: \(error)")
                    JLToast.makeText(Constants.error_messages.call_to_ws_toast).show()
                }
             }
         }
        else
        {
            JLToast.makeText(Constants.error_messages.error_not_enough_points_to_give).show()

        }
        
    }
    


}
