//
//  LoginViewController.swift
//  BeakonIOS
//
//  Created by Christopher on 8/23/16.
//  Copyright © 2016 CentauroSolutions. All rights reserved.
//

import UIKit
import Alamofire
import AlamofireObjectMapper
import SwiftyJSON
import CryptoSwift
class LoginViewController: UIViewController {
    @IBOutlet weak var passwordText: UITextField!
    @IBOutlet weak var usernameText: UITextField!
    @IBOutlet weak var loginBtn: UIButton!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        loginBtn.addTarget(self, action: #selector(LoginViewController.loginService), forControlEvents: .TouchUpInside)
    }

    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func loginService()
    {
        //Endpoint
        let url : String = "http://buserdevel.cfapps.io/user/username"
        //Obtiene el texto de los textView para realizar el request
        let username : String = self.usernameText.text!
        let passwordT : String = self.passwordText.text!.md5()
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
                            //Obtiene solo el objeto user de la respuesta
                            user = response.objectForKey("user")! as! NSDictionary
                            //obtiene la contraseña
                            let password = (user)["password"] as! String
                                //Pregunta si las contraseñas son iguales
                                if(password==passwordT)
                                {
                                    //Open background
                                    print("Ingresando")
                                    let vc = self.storyboard!.instantiateViewControllerWithIdentifier("background")
                                    self.showDetailViewController(vc as! BackgroundViewController, sender: self)
                                }
                                else
                                {
                                    print("Contraseña incorrecta")
                                }
                        }
                        //Si el status de la respuesta es 404 el usuario no esta registrado
                        else
                        {
                            print("El usuario no se encuentra registrado")
                        }
                    case .Failure(let error):
                        print("Hubo un error realizando la peticion: \(error)")
                }
          }
    }
    
        
    }


