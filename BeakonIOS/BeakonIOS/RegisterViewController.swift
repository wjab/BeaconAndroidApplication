//
//  RegisterViewController.swift
//  BeakonIOS
//
//  Created by Alejandra on 8/23/16.
//  Copyright © 2016 CentauroSolutions. All rights reserved.
//

import UIKit
import Alamofire
import SwiftyJSON
import JLToast
class RegisterViewController: UIViewController
{
    @IBOutlet weak var emailTF: UITextField!
    @IBOutlet weak var nameTF: UITextField!
    @IBOutlet weak var phoneTF: UITextField!
    @IBOutlet weak var lastnameTF: UITextField!
    @IBOutlet weak var passwordTF: UITextField!
    @IBOutlet weak var registerBtn: UIButton!
    let utils = UtilsC()
    override func viewDidLoad()
    {
        super.viewDidLoad()
        self.navigationItem.title = ""
        
        registerBtn.addTarget(self, action: #selector(RegisterViewController.loginService), forControlEvents: .TouchUpInside)

    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    func loginService()
    {
        //Endpoint
        let url : String = Constants.ws_services.user
        //Obtiene el texto de los textView para realizar el request
        let emailT : String = self.emailTF.text!
        print(emailT)
        //self.emailTF.selectedTextRange = self.emailTF.textRangeFromPosition(self.emailTF.beginningOfDocument, toPosition: self.emailTF.beginningOfDocument)
        let nameT : String = self.nameTF.text!
        let phoneT : String = self.phoneTF.text!
        let lastnameT : String = self.lastnameTF.text!
        let passwordT : String = self.passwordTF.text!
        let preferenceList = Array<Preference>();
        if(emailT != "" && nameT != "" && phoneT != "" && lastnameT != "" && passwordT != "")
        {
        if utils.isValidEmail(emailT){
            print("Validate EmailID")
            //parametros a enviar por body en el request
            let newTodo = ["user": emailT,
                           "password": passwordT,
                           "enable": true,
                           "categoryId":"0",
                           "totalGiftPoints":"0",
                           "name": nameT,
                           "lastName": lastnameT,
                           "phone": phoneT,
                           "creationDate": utils.convertLongToDate(),
                           "modifiedDate": utils.convertLongToDate(),
                           "email": emailT,
                           "socialNetworkId":"",
                           "socialNetworkType" : "localuser",
                           "socialNetworkJson": "",
                           "gender":  "",
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
                        if(String((response)["status"] as! Int) == Constants.ws_response_code.ok)
                        {
                            //Obtiene solo el objeto user de la respuesta
                            user = response.objectForKey("user")! as! NSDictionary
                            let vc = self.storyboard!.instantiateViewControllerWithIdentifier("Login")
                            self.showDetailViewController(vc as! LoginViewController, sender: self)
                            
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
       else{
            print("Email invalido")
            JLToast.makeText("Email invalido").show()
        }
        }
        else
        {
             JLToast.makeText("Favor ingresar todos los datos").show()
        }
    }


}
