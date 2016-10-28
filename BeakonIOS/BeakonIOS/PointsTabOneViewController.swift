//
//  PointsTabOneViewController.swift
//  BeakonIOS
//
//  Created by Alejandra on 8/26/16.
//  Copyright © 2016 CentauroSolutions. All rights reserved.
//

import UIKit
import Alamofire
import SwiftyJSON
import JLToast

extension NSDate {
    convenience init?(jsonDate: String) {
        let prefix = "/Date("
        let suffix = ")/"
        let scanner = NSScanner(string: jsonDate)
        
        // Check prefix:
        if scanner.scanString(prefix, intoString: nil) {
            
            // Read milliseconds part:
            var milliseconds : Int64 = 0
            if scanner.scanLongLong(&milliseconds) {
                // Milliseconds to seconds:
                var timeStamp = NSTimeInterval(milliseconds)/1000.0
                
                // Read optional timezone part:
                var timeZoneOffset : Int = 0
                if scanner.scanInteger(&timeZoneOffset) {
                    let hours = timeZoneOffset / 100
                    let minutes = timeZoneOffset % 100
                    // Adjust timestamp according to timezone:
                    timeStamp += NSTimeInterval(3600 * hours + 60 * minutes)
                }
                
                // Check suffix:
                if scanner.scanString(suffix, intoString: nil) {
                    // Success! Create NSDate and return.
                    self.init(timeIntervalSince1970: timeStamp)
                    return
                }
            }
        }
        
        // Wrong format, return nil. (The compiler requires us to
        // do an initialization first.)
        self.init(timeIntervalSince1970: 0)
        return nil
    }
}

class PointsTabOneViewController: UIViewController {

    @IBOutlet weak var pointsBtn: UITextField!
    @IBOutlet weak var messageL: UILabel!
    @IBOutlet weak var pointsL: UITextField!
     @IBOutlet weak var butonAction: UIButton!
    var pointsMinium = 0
    var userId = ""
    var code = ""
    var pointsUser = 0
    var dateExpiration:Int = 14
    var dateSend = ""
    override func viewDidLoad() {
        super.viewDidLoad()
        obtainMiniumPoints()
        butonAction.addTarget(self, action: #selector(PointsTabOneViewController.service), forControlEvents: .TouchUpInside)
        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func chargeData(){
        let defaults = NSUserDefaults.standardUserDefaults()
        self.userId = defaults.objectForKey("userId") as! String
        self.pointsUser = defaults.objectForKey("points") as! Int
        messageL.text = Constants.messages.availablePointsMessage(String(self.pointsUser), minimum: String(self.pointsMinium));
        
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
                        print(Constants.error_messages.call_to_ws_min_points)
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
                        NSNotificationCenter.defaultCenter().postNotificationName("refreshPoints", object: nil)
                        NSNotificationCenter.defaultCenter().postNotificationName("refreshPointsHome", object: nil)
                        self.pointsL.text = ""
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
                                //self.expirationDate = (pointsObject)["expirationDate"] as Float
                                JLToast.makeText("Creado con exito").show()
                                self.serviceUpdateUserDefault()
                                let secondViewController = self.storyboard?.instantiateViewControllerWithIdentifier("DetailChangePointsViewController") as! DetailChangePointsViewController
                                secondViewController.expiration = self.dateSend
                                secondViewController.code = self.code
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
