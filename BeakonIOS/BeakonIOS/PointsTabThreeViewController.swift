//
//  PointsTabThreeViewController.swift
//  BeakonIOS
//
//  Created by Alejandra on 9/2/16.
//  Copyright © 2016 CentauroSolutions. All rights reserved.
//

import UIKit
import Alamofire
import SwiftyJSON
import JLToast
class PointsTabThreeViewController: UIViewController {
    var userId = ""
    var code = ""
    @IBOutlet weak var codeL: UITextField!
    @IBOutlet weak var butonAction: UIButton!
    override func viewDidLoad() {
        super.viewDidLoad()
        let defaults = NSUserDefaults.standardUserDefaults()
        self.userId = defaults.objectForKey("userId") as! String
        butonAction.addTarget(self, action: #selector(PointsTabThreeViewController.service), forControlEvents: .TouchUpInside)
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    

    func serviceUpdateUserDefault()
    {
        let url : String = Constants.ws_services.user+"id/"+self.userId
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
    //D347E244 149
    func service()
    {
        let url : String = Constants.ws_services.utils+"redeemPoints"
        //Crea el request
        self.code = self.codeL.text!
        print(self.code)
        let newTodo : [String : AnyObject] =
            [
                "userId": self.userId,
                "code": self.code
            ]
            Alamofire.request(.POST, url, parameters: newTodo , encoding: .JSON).responseJSON
                {
                    response in switch response.result
                    {
                    //Si la respuesta es satisfactoria
                    case .Success(let JSON):
                        let response = JSON as! NSDictionary
                        var pointsObject = JSON as! NSDictionary
                        //Si la respuesta no tiene status 404
                        if(String((response)["status"] as! Int) == Constants.ws_response_code.ok)
                        {
                        
                                pointsObject = response.objectForKey("pointsData")! as! NSDictionary
                                let points = (pointsObject)["points"] as! Int
                                //self.expirationDate = (pointsObject)["expirationDate"] as Float
                                JLToast.makeText(Constants.messages.exchangedPointsMessage(String(points))).show()
                                self.serviceUpdateUserDefault()
                                self.codeL.text = ""
                        }
                        else if((response)["status"] as! Int == 400)
                        {
                            JLToast.makeText(Constants.error_messages.invalid_exchange_code).show()
                        }
                        else
                        {
                            JLToast.makeText(Constants.error_messages.error_exchange_points).show()
                        }
                    case .Failure(let error):
                        print("Hubo un error realizando la peticion: \(error)")
                        JLToast.makeText(Constants.error_messages.call_to_ws_toast).show()
                    }
            }
        
        
    }


}
