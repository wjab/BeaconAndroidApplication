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
    var notificationArray: [Notification] = []
    let defaults = NSUserDefaults.standardUserDefaults()
    var wishCount = 1
    
    var backgroundNotificationTask: UIBackgroundTaskIdentifier = UIBackgroundTaskInvalid
     var updateTimer : NSTimer?
    var intervalTimeSeg : double_t = 60.0
    
    func btnWishList(btn1:UIButton)->UIButton{
       
        return btn1
}
    
    func convertLongToDate() -> String{
        let newDate = NSDate()
        let formatter = NSDateFormatter()
        formatter.dateFormat = "dd/MM/yyyy HH:mm:ss.SSS"
        return formatter.stringFromDate(newDate)
    }
    
    //Cargar las  imagenes desde la url
    func loadImageFromUrl(url: String, view: UIImageView){
        let url = NSURL(string: url)!
        let task = NSURLSession.sharedSession().dataTaskWithURL(url) { (responseData, responseUrl, error) -> Void in
            if(error==nil){
                if let data = responseData{
                    view.image = UIImage(data: data)
                }
            }
            else{
                view.image = UIImage(named: "logo")
            }
        }
        task.resume()
    }
    
      func loadNewNotification(){
        
            let userId = defaults.objectForKey("userId") as? String
            //Endpoint
            let url : String = "http://butilsdevel.cfapps.io/notification/userId/"+userId!
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
                        self.notificationArray.removeAll()
                        if((response)["status"] as! String != "404")
                        {
                            let notificationList = response.mutableArrayValueForKey("notificationResult")
                            for (_, element) in notificationList.enumerate() {
                                let notificationObject = Notification()
                                notificationObject.idPropeties = element.objectForKey("id") as! String
                                notificationObject.messagePropeties = element.objectForKey("message") as! String
                                notificationObject.userIdPropeties = element.objectForKey("userId") as! String
                                notificationObject.typePropeties = element.objectForKey("type") as! String
                                notificationObject.readPropeties = element.objectForKey("read") as! Bool
                                notificationObject.creationDatePropeties = element.objectForKey("creationDate") as! Float
                                self.notificationArray.append(notificationObject)
                            }
                            //Setear el estado de si hay notoficacines nuevas o no
                            if(self.notificationArray.count>0){
                                 self.defaults.setObject(true, forKey: "stateIconNotification")
                                NSNotificationCenter.defaultCenter().postNotificationName("refreshIconNotification", object: nil)
                            }
                            else{
                                self.defaults.setObject(false, forKey: "stateIconNotification")
                                NSNotificationCenter.defaultCenter().postNotificationName("refreshIconNotification", object: nil)
                            }
                        }
                        else
                        {
                            print("Hubo un error obteniendo los datos de notificaciones")
                        }
                    case .Failure(let error):
                        print("Hubo un error realizando la peticion: \(error)")
                    }
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
        Alamofire.request(.POST, url, parameters: newTodo as? [String : AnyObject], encoding: .JSON)
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
                        if((response)["message"] as! String=="Product already added to wishlist")
                        {
                            JLToast.makeText("Este producto ya se encuentra en su lista de deseos").show()
                        }
                        else
                        {
                            //NSNotificationCenter.defaultCenter().postNotificationName("refreshIconWish", object: nil)
                            //Obtiene la lista de deseos
                            user = response.objectForKey("user")! as! NSDictionary
                            let productList = user.mutableArrayValueForKey("productWishList")
                            var wishListDefaults: [[NSObject : AnyObject]] = []
                            for (_, product) in productList.enumerate()
                            {
                                let wishObject = Wish()
                                wishObject.productIdPropeties = product.objectForKey("productId") as! String
                                wishObject.productNamePropeties = product.objectForKey("productName") as! String
                                wishObject.pricePropeties = product.objectForKey("price") as! Int
                                wishObject.imageUrlListPropeties = product.objectForKey("imageUrlList") as! String
                                wishObject.pointsByPricePropeties = product.objectForKey("pointsByPrice") as! Int
                                wishListDefaults.append(
                                    [
                                        "id": wishObject.productId ,
                                        "name": wishObject.productName,
                                        "price": wishObject.price,
                                        "image": wishObject.imageUrlList,
                                        "points": wishObject.pointsByPrice
                                    ])
                            }
                            self.defaults.setObject(wishListDefaults, forKey: "wishListUser")
                            JLToast.makeText("Añadido correctamente a su lista de deseos").show()
                            self.wishCount = self.defaults.objectForKey("wishCount")as!Int
                            let wish = self.wishCount + 1
                            self.defaults.setObject(wish, forKey: "wishCount")
                            self.refreshDatas()
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
    }
    
    func refreshDatas(){
        NSNotificationCenter.defaultCenter().postNotificationName("refreshWishCount", object: nil)
        NSNotificationCenter.defaultCenter().postNotificationName("refreshWishCountHome", object: nil)
        NSNotificationCenter.defaultCenter().postNotificationName("refreshWishCountFaq", object: nil)
        NSNotificationCenter.defaultCenter().postNotificationName("refreshWishCountDetailPromo", object: nil)
        NSNotificationCenter.defaultCenter().postNotificationName("refreshWishCountNotification", object: nil)
        NSNotificationCenter.defaultCenter().postNotificationName("refreshWishCountDetailCategory", object: nil)
        NSNotificationCenter.defaultCenter().postNotificationName("refreshWishCountDetailShop", object: nil)
        NSNotificationCenter.defaultCenter().postNotificationName("refreshWishCountProfile", object: nil)
        NSNotificationCenter.defaultCenter().postNotificationName("refreshWishCountPoints", object: nil)
         NSNotificationCenter.defaultCenter().postNotificationName("refreshWishCountDepartment", object: nil)
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
    
            if(id.isEmpty == false )
            {
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
                        let localNotification = UILocalNotification()
                        localNotification.fireDate = NSDate(timeIntervalSinceNow: 1)
                        localNotification.alertBody = "puntos obtenidos: "+String()
                        localNotification.timeZone = NSTimeZone.defaultTimeZone()
                        localNotification.applicationIconBadgeNumber = UIApplication.sharedApplication().applicationIconBadgeNumber + 1
                        
                        UIApplication.sharedApplication().scheduleLocalNotification(localNotification)
                        
                         NSNotificationCenter.defaultCenter().postNotificationName("refreshPoints", object: nil)
                        }
                    else if((response)["status"]as! Int == 400){
                        print("El usuario ha superado el límite de escaneos y/o el intervalo de escaneo no se ha cumplido")
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
    

}
