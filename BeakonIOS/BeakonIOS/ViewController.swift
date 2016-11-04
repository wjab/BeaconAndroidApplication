//
//  ViewController.swift
//  BeakonIOS
//
//  Created by Alejandra on 8/23/16.
//  Copyright © 2016 CentauroSolutions. All rights reserved.
//

import UIKit
import Alamofire
import SwiftyJSON
import JLToast
import CryptoSwift

// Librerias para comunicacion con facebook
import FBSDKCoreKit
import FBSDKLoginKit

class ViewController: UIViewController 
{
    let url = "http://buserdevel.cfapps.io/user"
    
    @IBOutlet weak var btnFacebookCustom : UIButton?
    
    let defaults = NSUserDefaults.standardUserDefaults()
    let utils = UtilsC()
    var idFacebook = ""
    
    override func viewDidLoad()
    {
        super.viewDidLoad()
        self.navigationItem.title = ""
        
        btnFacebookCustom!.addTarget(self, action: #selector(ViewController.loginFacebookAction), forControlEvents: .TouchUpInside)        
    }
    
    @IBAction func loginFacebookAction(sender : AnyObject)
    {
        let fbLoginManager : FBSDKLoginManager = FBSDKLoginManager()
        
        fbLoginManager.logInWithReadPermissions(["public_profile", "email", "user_friends"], fromViewController: self){ (result, error) -> Void in
            
            if(error == nil)
            {
                self.getInfoFacebook()
            }
            else
            {
                JLToast.makeText(Constants.facebook.error_general).show()
            }
        }
    }
    
    func loginButton(loginButton: FBSDKLoginButton!, didCompleteWithResult result: FBSDKLoginManagerLoginResult!, error: NSError!)
    {
        self.getInfoFacebook()
    }
    
    func getInfoFacebook()
    {
        FBSDKGraphRequest.init(graphPath: "me", parameters: ["fields":"email, name, gender, id, first_name, last_name, picture.type(large)"]).startWithCompletionHandler { (connection, result, error) -> Void in
            //Obtiene los datos de facebook del usuario
            let strFirstName: String = (result.objectForKey("first_name") as? String)!
            let strLastName: String = (result.objectForKey("last_name") as? String)!
            let strName: String = (result.objectForKey("name") as? String)!
            self.idFacebook = (result.objectForKey("id") as? String)!
            let strGender: String = (result.objectForKey("gender") as? String)!
            let strEmail: String = (result.objectForKey("email") as? String)!
           // let strBirthday: String = (result.objectForKey("user_birthday") as? String)!
            //let strPhone: String = (result.objectForKey("phone") as? String)!
            let strPictureURL: String = (result.objectForKey("picture")?.objectForKey("data")?.objectForKey("url") as? String)!
            print(result)
            //Crea el json a guardar en socialNetworkJson
            let userData:NSMutableDictionary = NSMutableDictionary()
            userData.setValue(strGender, forKey: "gender")
            userData.setValue(strFirstName, forKey: "first_name")
            userData.setValue(strLastName, forKey: "last_name")
            userData.setValue(strName, forKey: "name")
            userData.setValue(self.idFacebook, forKey: "id")
            let jsonData = try! NSJSONSerialization.dataWithJSONObject(userData, options: NSJSONWritingOptions())
            let jsonString = NSString(data: jsonData, encoding: NSUTF8StringEncoding) as! String
            print(jsonString)
            
            self.getUserByUsername(jsonData, username: strName, firstname: strFirstName, lastname: strLastName, gender:  strGender, id: self.idFacebook, email: strEmail, image: strPictureURL)
            
            }
    }
    
    func getUserByUsername(json:NSData, username:String, firstname:String, lastname:String, gender:String, id:String, email:String, image:String)
    {
        //Endpoint
        let url : String = Constants.ws_services.user+"username"
        //parametros a enviar por body en el request
        let newTodo = ["username": username]
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
                    if((response)["status"] as! Int != 404)
                    {
                          user = response.objectForKey("user")! as! NSDictionary
                        print((response)["status"])
                            if((user)["enable"] as! Bool == true)
                            {
                                if((user)["socialNetworkType"] as! String != "localuser"){
                                    let appDomain = NSBundle.mainBundle().bundleIdentifier!
                                    NSUserDefaults.standardUserDefaults().removePersistentDomainForName(appDomain)
                                    let defaults = NSUserDefaults.standardUserDefaults()
                                    
                                    defaults.setObject((user)["user"] as! String, forKey: "username")
                                    defaults.setObject((user)["id"] as! String, forKey: "userId")
                                    defaults.setObject((user)["enable"] as! Bool, forKey: "enable")
                                    defaults.setObject((user)["totalGiftPoints"] as! Int, forKey: "points")
                                    defaults.setObject((user)["pathImage"] as! String, forKey: "image")
                                    defaults.setObject((user)["name"] as! String, forKey: "name")
                                    defaults.setObject((user)["lastName"] as! String, forKey: "lastname")
                                    defaults.setObject((user)["email"] as! String, forKey: "email")
                                    defaults.setObject((user)["gender"] as! String, forKey: "gender")
                                    defaults.setObject((user)["socialNetworkType"] as! String, forKey: "socialNetworkType")
                                    defaults.setObject(image, forKey: "image")
                                    defaults.setObject((user)["gender"] as! String, forKey: "gender")
                                    defaults.setObject((user)["phone"] as! String, forKey: "phone")
                                    defaults.setObject(self.idFacebook, forKey: "id")
                                    //HomeTabViewController.utils.loadNewNotification()
                                    HomeTabViewController.konkat.viewDidLoad()
                                    //HomeTabViewController.utils.initBackgrounNotification()
                                    let productList = user.mutableArrayValueForKey("productWishList")
                                    defaults.setObject(productList.count, forKey: "wishCount")
                                    let vc = self.storyboard!.instantiateViewControllerWithIdentifier("Navigation")
                                    self.showDetailViewController(vc as! NavigationViewController, sender: self)
                                }
                                else
                                {
                                    print("Usuario no asociado a una red social")
                                    JLToast.makeText("El usuario no se encuentra asociado a una red social").show()
                                }
                            }
                            else
                            {
                                print("Usuario desactivado")
                                JLToast.makeText("El usuario se encuentra desactivado").show()
                            }
                            
                        }
                        //Si el status de la respuesta es 404 el usuario no esta registrado, se crea el usuario
                    else
                    {
                        self.createUserSocial(json, username: username, firstname: firstname, lastname: lastname, gender: gender, id: id, email: email, image: image)
                    }
    
                case .Failure(let error):
                    print("Hubo un error realizando la peticion: \(error)")
                    JLToast.makeText("Hubo un error realizando la petición").show()
                }
        }
 
        //Validar que sea un usuario de typo social
            //Guarda los datos en UserDefaults
    }

    func createUserSocial(json:NSData, username:String, firstname:String, lastname:String, gender:String, id:String, email:String, image:String)
    {
        //Endpoint
        let url : String = Constants.ws_services.user
        let preferenceList = Array<Preference>();
                //parametros a enviar por body en el request
                let newTodo = ["user": username,
                               "password": id.md5(),
                               "enable": true,
                               "categoryId":"0",
                               "totalGiftPoints":"0",
                               "name": firstname,
                               "lastName": lastname,
                               "phone": "",
                               "creationDate": utils.convertLongToDate(),
                               "modifiedDate": utils.convertLongToDate(),
                               "email": email,
                               "socialNetworkId":id,
                               "socialNetworkType" : "facebook",
                               "socialNetworkJson": "",
                               "gender":  gender,
                               "pathImage":"",
                               "preference": preferenceList]
                
                //Crea el request
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
                                //Obtiene solo el objeto user de la respuesta
                                user = response.objectForKey("user")! as! NSDictionary
                                let appDomain = NSBundle.mainBundle().bundleIdentifier!
                                NSUserDefaults.standardUserDefaults().removePersistentDomainForName(appDomain)
                                let defaults = NSUserDefaults.standardUserDefaults()
                                
                                defaults.setObject((user)["user"] as! String, forKey: "username")
                                defaults.setObject((user)["id"] as! String, forKey: "userId")
                                defaults.setObject((user)["enable"] as! Bool, forKey: "enable")
                                defaults.setObject((user)["totalGiftPoints"] as! Int, forKey: "points")
                                defaults.setObject((user)["pathImage"] as! String, forKey: "image")
                                defaults.setObject((user)["name"] as! String, forKey: "name")
                                defaults.setObject((user)["lastName"] as! String, forKey: "lastname")
                                defaults.setObject((user)["email"] as! String, forKey: "email")
                                defaults.setObject((user)["socialNetworkType"] as! String, forKey: "socialNetworkType")
                                defaults.setObject((user)["gender"] as! String, forKey: "gender")
                                defaults.setObject((user)["phone"] as! String, forKey: "phone")
                                defaults.setObject(image, forKey: "image")
                                //defaults.setObject((user)["gender"] as! String, forKey: "gender")
                                //HomeTabViewController.utils.loadNewNotification()
                                HomeTabViewController.konkat.viewDidLoad()
                                //HomeTabViewController.utils.initBackgrounNotification()
                                let vc = self.storyboard!.instantiateViewControllerWithIdentifier("Navigation")
                                self.showDetailViewController(vc as! NavigationViewController, sender: self)
                            }
                            else
                            {
                                print("El usuario no se ha registrado correctamente")
                                JLToast.makeText("El usuario no se ha registrado correctamente").show()
                            }
                        case .Failure(let error):
                            print("Hubo un error realizando la peticion: \(error)")
                            JLToast.makeText("Hubo un error realizando la petición").show()
                        }
        }

    }
    
    func loginButtonDidLogOut(loginButton: FBSDKLoginButton!)
    {
        let loginManager: FBSDKLoginManager = FBSDKLoginManager()
        loginManager.logOut()
    }

}

