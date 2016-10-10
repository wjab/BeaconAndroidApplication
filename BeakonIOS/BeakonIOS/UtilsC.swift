//
//  UtilsC.swift
//  BeakonIOS
//
//  Created by Alejandra on 8/29/16.
//  Copyright © 2016 CentauroSolutions. All rights reserved.
//

import UIKit
import Alamofire
import SwiftyJSON
import JLToast

class UtilsC: UIViewController {
    
    var webServiceCategory = "http://bcategorydevel.cfapps.io/"
    var webServiceDevice = "http://bdevicedevel.cfapps.io/"
    var webServiceMerchantProduct = "http://bmerchantproductdevel.cfapps.io/"
    var webServiceMerchantProfile = "http://bmerchantprofiledevel.cfapps.io/"
    var webServiceMobile = "http://bmobiledevel.cfapps.io/"
    var webServiceOfferHistory = "http://bofferhistorydevel.cfapps.io/"
    var webServicePromo = "http://bpromodevel.cfapps.io/"
    var webServiceUser = "http://buserdevel.cfapps.io/"
    var webServiceUtils = "http://butilsdevel.cfapps.io/"
    var webServiceFaq = "http://bfaqdevel.cfapps.io/"
    var webServiceHistoryPointsUser = "http://butilsdevel.cfapps.io/utils/user/getPointsData/"
    var webService_VisitorHistory = "http://bvisitorhistorydevel.cfapps.io/"
    var webServicePoints = "http://bpointsdevel.cfapps.io/"
    
    let defaults = NSUserDefaults.standardUserDefaults()
    
    func convertLongToDate() -> String{
        let newDate = NSDate()
        let formatter = NSDateFormatter()
        formatter.dateFormat = "dd/MM/yyyy HH:mm:ss.SSS"
        return formatter.stringFromDate(newDate)
    }
    
        
    func isValidEmail(testStr:String) -> Bool {
        print("validate emilId: \(testStr)")
        let emailRegEx = "^(?:(?:(?:(?: )*(?:(?:(?:\\t| )*\\r\\n)?(?:\\t| )+))+(?: )*)|(?: )+)?(?:(?:(?:[-A-Za-z0-9!#$%&’*+/=?^_'{|}~]+(?:\\.[-A-Za-z0-9!#$%&’*+/=?^_'{|}~]+)*)|(?:\"(?:(?:(?:(?: )*(?:(?:[!#-Z^-~]|\\[|\\])|(?:\\\\(?:\\t|[ -~]))))+(?: )*)|(?: )+)\"))(?:@)(?:(?:(?:[A-Za-z0-9](?:[-A-Za-z0-9]{0,61}[A-Za-z0-9])?)(?:\\.[A-Za-z0-9](?:[-A-Za-z0-9]{0,61}[A-Za-z0-9])?)*)|(?:\\[(?:(?:(?:(?:(?:[0-9]|(?:[1-9][0-9])|(?:1[0-9][0-9])|(?:2[0-4][0-9])|(?:25[0-5]))\\.){3}(?:[0-9]|(?:[1-9][0-9])|(?:1[0-9][0-9])|(?:2[0-4][0-9])|(?:25[0-5]))))|(?:(?:(?: )*[!-Z^-~])*(?: )*)|(?:[Vv][0-9A-Fa-f]+\\.[-A-Za-z0-9._~!$&'()*+,;=:]+))\\])))(?:(?:(?:(?: )*(?:(?:(?:\\t| )*\\r\\n)?(?:\\t| )+))+(?: )*)|(?: )+)?$"
        let emailTest = NSPredicate(format:"SELF MATCHES %@", emailRegEx)
        let result = emailTest.evaluateWithObject(testStr)
        return result
    }
    
    func addWishList(wishProduct:Product)
    {
        let url : String = "http://buserdevel.cfapps.io/user/wishlist/add"
        let idUser = (defaults.objectForKey("userId") as? String)!
        let name = wishProduct.productNamePropeties
        let id = wishProduct.productIdPropeties
        let price = wishProduct.pricePropeties
        let urlImage = wishProduct.imageUrlListPropeties[0]
             let newTodo = [
                        "userId": idUser,
                        "productName": name,
                       "productId": id,
                       "price": price,
                       "imageUrlList":urlImage,
                        "pointsByPrice": 0]
        Alamofire.request(.POST, url, parameters: newTodo as? [String : AnyObject], encoding: .JSON)
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
                        if((response)["message"] as! String=="Product already added to wishlist"){
                            JLToast.makeText("Este producto ya se encuentra en la lista de deseos").show()
                        }
                        else{
                        print("Genial")
                        JLToast.makeText("Añadido correctamente").show()
                        }

                    }
                    else
                    {
                        print("ERROR")
                    }
                case .Failure(let error):
                    print("Hubo un error realizando la peticion: \(error)")
                }
        }
        print(wishProduct.productNamePropeties)
    }
    
    //Abre el historial de puntos
    func clickOnButton(button: UIButton) {
        print("touch")
    }
    //Abre la lista de deseos
    func openWishList(){
        let vc = self.storyboard!.instantiateViewControllerWithIdentifier("WishViewController")
        self.showDetailViewController(vc as! WishViewController, sender: self)
    }
    
    //Get by Id
    func serviceById(id:String){
        //Endpoint
        let idUser = (defaults.objectForKey("userId") as? String)!
        let newTodo = [
            "userId": idUser,
            "promoId": id
        ]

        let url = "http://butilsdevel.cfapps.io/utils/savePoints"
        //Crea el request
        Alamofire.request(.POST, url, parameters: newTodo, encoding: .JSON)
            .responseJSON
            {
                response in switch response.result
                {
                //Si la respuesta es satisfactoria
                case .Success(let JSON):
                    let response = JSON as! NSDictionary
                    var user = JSON as! NSDictionary
                    //Si la respuesta no tiene status 404
                    if((response)["status"] as! Int != 404 && (response)["status"]as! Int != 400)
                    {
                        user = response.objectForKey("user")! as! NSDictionary
                        let defaults = NSUserDefaults.standardUserDefaults()
                        defaults.setObject((user)["totalGiftPoints"] as! Int, forKey: "points")
                        JLToast.makeText("Puntos obtenidos").show()
                    }
                    else if((response)["status"]as! Int == 400){
                        JLToast.makeText("El usuario ha superado el límite de escaneos y/o el intervalo de escaneo no se ha cumplido").show()
                    }
                    else
                    {
                        print("Hubo un error obteniendo los datos de promociones")
                    }
                case .Failure(let error):
                    print("Hubo un error realizando la peticion: \(error)")
                }
        }
    }
    

}
