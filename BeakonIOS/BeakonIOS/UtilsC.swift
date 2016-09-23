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
   static var wishArray: [Wish] = []
    
    func convertLongToDate() -> String{
        let newDate = NSDate()
        let formatter = NSDateFormatter()
        formatter.dateFormat = "dd/MM/yyyy HH:mm:ss.SSS"
        return formatter.stringFromDate(newDate)
    }
    
    func obtainWishListUser(productRecieve:Product){
        let idUser = (defaults.objectForKey("userId") as? String)!
        //Endpoint
        let url : String = "http://buserdevel.cfapps.io/user/id/"+idUser
        Alamofire.request(.GET, url, encoding: .JSON)
            .responseJSON
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
                        print((response)["status"])
                        user = response.objectForKey("user")! as! NSDictionary
                        let productList = user.mutableArrayValueForKey("productWishList")
                        for (indexP, product) in productList.enumerate()
                        {
                            print(indexP, ":", product)
                            let wishObject = Wish()
                            wishObject.productIdPropeties = product.objectForKey("productId") as! String
                            wishObject.productNamePropeties = product.objectForKey("productName") as! String
                            wishObject.pricePropeties = product.objectForKey("price") as! Int
                            wishObject.imageUrlListPropeties = product.objectForKey("imageUrlList") as! String
                            wishObject.pointsByPricePropeties = product.objectForKey("pointsByPrice") as! Int
                            if(wishObject.productIdPropeties == productRecieve.productIdPropeties){
                                //Si si es igual se actualiza la propiedad
                                wishObject.isAddedPropeties = true
                            }

                           UtilsC.wishArray.append(wishObject)
                        }
                        print("-------------------------------- 1 ------------------------------------------")
                        print(UtilsC.wishArray[0].productName,UtilsC.wishArray[0].isAdded)
                    }
                    else
                    {
                        print("Hubo un error obteniendo los datos de lista de deseos")
                    }
                case .Failure(let error):
                    print("Hubo un error realizando la peticion: \(error)")
                }
                print("------------------------ 2 --------------------------------------------------")
                print(UtilsC.wishArray[0].productName,UtilsC.wishArray[0].isAdded)
        }
      
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
        
        print(url)
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
                        print("Genial")
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
}
