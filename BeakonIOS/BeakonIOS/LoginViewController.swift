//
//  LoginViewController.swift
//  BeakonIOS
//
//  Created by Alejandra on 8/23/16.
//  Copyright © 2016 CentauroSolutions. All rights reserved.
//

import UIKit
import Alamofire
import SwiftyJSON
import CryptoSwift
import JLToast

class LoginViewController: UIViewController , UITextFieldDelegate{
    @IBOutlet weak var passwordText: UITextField!
    @IBOutlet weak var usernameText: UITextField!
    @IBOutlet weak var loginBtn: UIButton!
    let utils = UtilsC()
    override func viewDidLoad() {
        super.viewDidLoad()
        loginBtn.addTarget(self, action: #selector(LoginViewController.loginService), forControlEvents: .TouchUpInside)
    }
    
    func textFieldShouldReturn(textField: UITextField) -> Bool {
        
        self.view.endEditing(true)
        
        return true
        
    }
    override func touchesBegan(touches: Set<UITouch>, withEvent event: UIEvent?) {
        
        usernameText.resignFirstResponder()
        passwordText.resignFirstResponder()
        
    }
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func loginService()
    {
        if(usernameText.text != "" && passwordText.text != "")
        {
            if (utils.isValidEmail(usernameText.text!))
            {
                //Endpoint
                let url : String = "http://buserdevel.cfapps.io/user/username"
                //Obtiene el texto de los textView para realizar el request
                let username : String = self.usernameText.text!
                let passwordT : String = self.passwordText.text!
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
                                print((response)["status"])
                                //Obtiene solo el objeto user de la respuesta
                                user = response.objectForKey("user")! as! NSDictionary
                                //obtiene la contraseña
                                let password = (user)["password"] as! String
                                //Pregunta si las contraseñas son iguales
                                if(password==passwordT.md5())
                                {
                                    if((user)["enable"] as! Bool == true)
                                    {
                                        print("Ingresando")
                                                                              
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
                                        //defaults.setObject((user)["pathImage"] as! String, forKey: "image")
                                        //defaults.setObject((user)["gender"] as! String, forKey: "gender")
                                        let vc = self.storyboard!.instantiateViewControllerWithIdentifier("Navigation")
                                        self.showDetailViewController(vc as! NavigationViewController, sender: self)
                                    }
                                    else
                                    {
                                        print("Usuario desactivado")
                                        JLToast.makeText("El usuario se encuentra desactivado").show()
                                    }
                                    
                                }
                                else
                                {
                                    print("Contraseña incorrecta")
                                    JLToast.makeText("Contraseña incorrecta").show()
                                }
                            }
                                //Si el status de la respuesta es 404 el usuario no esta registrado
                            else
                            {
                                print("El usuario no se encuentra registrado")
                                JLToast.makeText("El usuario no se encuentra registrado").show()
                            }
                        case .Failure(let error):
                            print("Hubo un error realizando la peticion: \(error)")
                            JLToast.makeText("Hubo un error realizando la petición").show()
                        }
                    }
                }
                else
                {
                    JLToast.makeText("El correo es invalido").show()
                }
            }
            else
            {
                JLToast.makeText("Favor ingrese todos los datos").show()
            }
        }
    
        
    }


