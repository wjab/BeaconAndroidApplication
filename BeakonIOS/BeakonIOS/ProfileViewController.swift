//
//  ProfileViewController.swift
//  BeakonIOS
//
//  Created by Alejandra on 8/25/16.
//  Copyright © 2016 CentauroSolutions. All rights reserved.
//

import UIKit
import Alamofire
import JLToast
import SwiftyJSON

extension UIApplication {
    class func tryURL(urls: [String]) {
        let application = UIApplication.sharedApplication()
        for url in urls {
            if application.canOpenURL(NSURL(string: url)!) {
                application.openURL(NSURL(string: url)!)
                return
            }
        }
    }
}

class ProfileViewController: UIViewController {
    @IBOutlet weak var datePicker: UIDatePicker!
    @IBOutlet weak var numberTF: UITextField!
    @IBOutlet weak var emailTF: UITextField!
    @IBOutlet weak var lastnameTF: UITextField!
    @IBOutlet weak var nameTF: UITextField!
    @IBOutlet weak var facebookBtn: UIButton!
    @IBOutlet weak var womenRadioButton: UIButton!
    @IBOutlet weak var menRadioButton: UIButton!
    var btn1 = UIButton()
    let defaults = NSUserDefaults.standardUserDefaults()
    var typerUser = ""
    var wishCount = 1
    var idFacebook = ""
    var points = 0
    let utils = UtilsC()
    var gender = ""
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.navigationItem.title = ""
        nameTF.text = defaults.objectForKey("name") as? String
        lastnameTF.text = defaults.objectForKey("lastname") as? String
        emailTF.text = defaults.objectForKey("email") as? String
        numberTF.text = defaults.objectForKey("phone")as?String
        self.typerUser = defaults.objectForKey("socialNetworkType") as! String
        self.wishCount = defaults.objectForKey("wishCount")as!Int
        self.points = defaults.objectForKey("points") as! Int
        self.gender = defaults.objectForKey("gender") as! String
        self.validateStateUser()
        
        //Genera el boton de la derecha que contiene el corazon que abre la lista de deseos
        btn1 = Utils.loadWishListButton(btn1, wishCount: wishCount)
        btn1.addTarget(self, action: #selector(ProfileViewController.openWishList), forControlEvents: .TouchUpInside)
        self.navigationItem.setRightBarButtonItem(UIBarButtonItem(customView: btn1), animated: true);
        
        // Crea el view con el label de puntos y el arrow de imagen
        let myView = Utils.createPointsView(points, activateEvents: true)
        let gesture = UITapGestureRecognizer(target : self, action: #selector(ProfileViewController.clickOnButton))
        myView.addGestureRecognizer(gesture)
        self.navigationItem.titleView = myView
        NSNotificationCenter.defaultCenter().addObserver(self, selector: #selector(ProfileViewController.refreshWishCount),name:"refreshWishCountProfile", object: nil)
        
        if(defaults.objectForKey("socialNetworkType")as!String == "facebook"){
            facebookBtn.setTitle("Ir a Perfil", forState: .Normal)
            self.idFacebook = defaults.objectForKey("id")as!String
            numberTF.userInteractionEnabled = false
            womenRadioButton.userInteractionEnabled = false
            menRadioButton.userInteractionEnabled = false
            facebookBtn.addTarget(self, action: #selector(ProfileViewController.openFacebookProfile), forControlEvents: UIControlEvents.TouchUpInside)
        }
        else
        {
            facebookBtn.setTitle("Actualizar información", forState: .Normal)
            emailTF.userInteractionEnabled = false
            facebookBtn.addTarget(self, action: #selector(ProfileViewController.updateProfile), forControlEvents: UIControlEvents.TouchUpInside)
            womenRadioButton.addTarget(self, action: #selector(ProfileViewController.updateWoman), forControlEvents: .TouchUpInside)
            menRadioButton.addTarget(self, action: #selector(ProfileViewController.updateMen), forControlEvents: .TouchUpInside)
            datePicker.addTarget(self, action: #selector(ProfileViewController.datePickerChanged(_:)), forControlEvents: .ValueChanged)
        }
         self.validateGender()
    }
    
    func datePickerChanged(datePicker:UIDatePicker) {
        let dateFormatter = NSDateFormatter()
        dateFormatter.dateStyle = NSDateFormatterStyle.ShortStyle
        let strDate = dateFormatter.stringFromDate(datePicker.date)
        defaults.setObject(strDate, forKey: "date")
        //datePicker.hidden = true
        // dateTF.text = strDate
    }
    
    func validateGender(){
        if(self.gender == "female"){
            updateWoman()
        }
        else{
            updateMen()
        }
    }
    
    
    func updateProfile(){
        let phone = numberTF.text
        let name = nameTF.text
        let lastname = lastnameTF.text
        let pathImage = defaults.objectForKey("image")
        let id = defaults.objectForKey("userId") as! String
        //Request actualiza el perfil de usuario
        let url : String = Constants.ws_services.user+id
        if(name != "" && phone != "" && lastname != "" && name != "")
        {
                //parametros a enviar por body en el request
                let newTodo: [String: AnyObject] = [
                               "name": name!,
                               "lastName": lastname!,
                               "phone": phone!,
                               "modifiedDate": utils.convertLongToDate(),
                               "gender": self.gender,
                               "pathImage": pathImage!,
                                "preference": NSNull(),
                                "productWishList": NSNull()
                              ]
                
                //Crea el request
                Alamofire.request(.PUT, url, parameters: newTodo as? [String : AnyObject], encoding: .JSON)
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
                                //Obtiene solo el objeto user de la respuesta
                                self.defaults.setObject((user)["gender"] as! String, forKey: "gender")
                                self.defaults.setObject((user)["phone"] as! String, forKey: "phone")
                                self.defaults.setObject((user)["name"] as! String, forKey: "name")
                                self.defaults.setObject((user)["lastName"] as! String, forKey: "lastname")
                                print(self.defaults.objectForKey("gender") as! String)
                                 JLToast.makeText("El usuario se ha actualizado correctamente").show()
                            }
                            else
                            {
                                JLToast.makeText("El usuario no se ha actualizado correctamente").show()
                            }
                        case .Failure(let error):
                            print("Hubo un error realizando la peticion: \(error)")
                            JLToast.makeText("Hubo un error realizando la petición").show()
                        }
                }
        }
        else
        {
            JLToast.makeText("Favor ingresar todos los datos").show()
        }
    }
    
    func updateMen(){
        womenRadioButton.setBackgroundImage(UIImage(named: "radioButton"), forState: .Normal)
        menRadioButton.setBackgroundImage(UIImage(named: "radioButtonSelected"), forState: .Normal)
        self.gender = "men"
    }
    
    func updateWoman(){
        menRadioButton.setBackgroundImage(UIImage(named: "radioButton"), forState: .Normal)
        womenRadioButton.setBackgroundImage(UIImage(named: "radioButtonSelected"), forState: .Normal)
        self.gender = "female"
    }
    
    func openFacebookProfile(){
        UIApplication.tryURL([
            "fb://profile/"+idFacebook,
            "http://www.facebook.com/"+idFacebook
            ])
    }
    
    func refreshWishCount(){
        //Refresca el contador
        self.wishCount = defaults.objectForKey("wishCount")as!Int
        btn1.setTitle(String(self.wishCount), forState: .Normal)
    }
    
    //Abre el menu
    func openMenu(){
        let secondViewController = self.storyboard?.instantiateViewControllerWithIdentifier("MenuContainerViewController") as! MenuContainerViewController
        self.navigationController?.pushViewController(secondViewController, animated: true)
    }
    //Abre el historial de puntos
    func clickOnButton() {
        let secondViewController = self.storyboard?.instantiateViewControllerWithIdentifier("HistoryPointsViewController") as! HistoryPointsViewController
        self.navigationController?.pushViewController(secondViewController, animated: true)
    }
    //Abre la lista de deseos
    func openWishList(){
        let secondViewController = self.storyboard?.instantiateViewControllerWithIdentifier("WishViewController") as! WishViewController
        self.navigationController?.pushViewController(secondViewController, animated: true)
    }
    
    
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    
    func validateStateUser()
    {   print(self.typerUser)
        if(self.typerUser == "localuser")
        {
            numberTF.userInteractionEnabled = false
            //dateTF.userInteractionEnabled = false
        }
        else
        {
            emailTF.userInteractionEnabled = false
            numberTF.userInteractionEnabled = false
            //dateTF.userInteractionEnabled = false
            nameTF.userInteractionEnabled = false
            lastnameTF.userInteractionEnabled = false
            
        }
    }
    
}
