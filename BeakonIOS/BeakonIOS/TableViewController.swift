//
//  TableViewController.swift
//  BeakonIOS
//
//  Created by Alejandra on 9/21/16.
//  Copyright © 2016 CentauroSolutions. All rights reserved.
//

import UIKit
import Alamofire
import SwiftyJSON

class TableViewController: UITableViewController , UIImagePickerControllerDelegate{
   @IBOutlet weak var preferencesButton: UIButton!
    @IBOutlet weak var profileButton: UIButton!
    @IBOutlet weak var wishButton: UIButton!
    @IBOutlet weak var notificationButton: UIButton!
    @IBOutlet weak var questionButton: UIButton!
    @IBOutlet weak var pointsButton: UIButton!
    @IBOutlet weak var logoutButton: UIButton!
    @IBOutlet weak var shareAppButton: UIButton!
    var preference:String!
    var state:String!
    let imagePicker = UIImagePickerController()
    let defaults = NSUserDefaults.standardUserDefaults()
    var arrayLabel = [UILabel]()
    let shareContent:String = "Conoces acerca de QuickShop, descargalo aqui http://quickshop.com"
    
    override func viewDidLoad() {
        super.viewDidLoad()
        preferencesButton.addTarget(self, action: #selector(TableViewController.createAlertPreferences), forControlEvents: .TouchUpInside)
        profileButton.addTarget(self, action: #selector(TableViewController.openProfile), forControlEvents: .TouchUpInside)
        wishButton.addTarget(self, action: #selector(TableViewController.openWish), forControlEvents: .TouchUpInside)
        notificationButton.addTarget(self, action: #selector(TableViewController.openNotification), forControlEvents: .TouchUpInside)
        questionButton.addTarget(self, action: #selector(TableViewController.openQuestions), forControlEvents: .TouchUpInside)
        pointsButton.addTarget(self, action: #selector(TableViewController.openPoints), forControlEvents: .TouchUpInside)
         shareAppButton.addTarget(self, action: #selector(TableViewController.share), forControlEvents: .TouchUpInside)
        logoutButton.addTarget(self, action: #selector(TableViewController.logout), forControlEvents: .TouchUpInside)
    }
    
    func openProfile(){
        let secondViewController = self.storyboard?.instantiateViewControllerWithIdentifier("ProfileViewController") as! ProfileViewController
        self.navigationController?.pushViewController(secondViewController, animated: true)
    }
    
    func openPoints(){
        let secondViewController = self.storyboard?.instantiateViewControllerWithIdentifier("PointsTabViewController") as! PointsTabViewController
        self.navigationController?.pushViewController(secondViewController, animated: true)
    }

    func share(){
        let activityViewController = UIActivityViewController(activityItems: [shareContent as NSString], applicationActivities: nil)
        presentViewController(activityViewController, animated: true, completion: {})
        
    }
    
    func openQuestions(){
        let secondViewController = self.storyboard?.instantiateViewControllerWithIdentifier("FAQTabViewController") as! FAQTabViewController
        self.navigationController?.pushViewController(secondViewController, animated: true)
    }

    func openNotification(){
        let secondViewController = self.storyboard?.instantiateViewControllerWithIdentifier("NotificationTabViewController") as! NotificationTabViewController
        self.navigationController?.pushViewController(secondViewController, animated: true)
    }
    
    func openWish(){
        let secondViewController = self.storyboard?.instantiateViewControllerWithIdentifier("WishViewController") as! WishViewController
        self.navigationController?.pushViewController(secondViewController, animated: true)
    }
    
    func logout(){
        let typeUser = defaults.objectForKey("socialNetworkType")as! String
        let appDomain = NSBundle.mainBundle().bundleIdentifier!
        NSUserDefaults.standardUserDefaults().removePersistentDomainForName(appDomain)
        if (typeUser == "facebook"){
         FBSDKLoginManager().logOut()
        }
        HomeTabViewController.konkat.endBackgroundTask()
        HomeTabViewController.konkat.devicesManager.stopDevicesDiscovery()
        let vc = self.storyboard!.instantiateViewControllerWithIdentifier("ViewController")
        self.showDetailViewController(vc as! ViewController, sender: self)
}
 
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    func createSwitch (value1:CGFloat ,value2:CGFloat, tag:Int) -> UISwitch{
        let switchControl = UISwitch(frame:CGRectMake(value1, value2, 0, 0));
        switchControl.on = true
        switchControl.tag = tag
        switchControl.setOn(true, animated: false);
        switchControl.addTarget(self, action: #selector(TableViewController.switchValueDidChange(_:)), forControlEvents: .ValueChanged);
        return switchControl
    }

    func createLabel(value1:CGFloat ,value2:CGFloat, textLabel:String , tag:Int) -> UILabel{
        let label = UILabel(frame: CGRectMake(value1, value2, 400.0, 30.0))
        label.textAlignment = NSTextAlignment.Left
        label.tag = tag
        label.text = textLabel
        arrayLabel.append(label)
        //Hacer un array de label staticos y con el tag del switch buscar el label correspondiente en el array y on=btener e,l esstado
        return label
    }
    
    func switchValueDidChange(sender:UISwitch!){
        //WebServiceUser/user/preference/editUser
        //Send userId, preference, state
        //POST
        let tagSwitchForUpdate = sender.tag
        let tagLabel = tagSwitchForUpdate - 100
        let obtainState = sender.on
        let obtainPreferences = self.arrayLabel[tagLabel].text
        let stateToSend:String!
        print(obtainPreferences, "----", obtainState)
        if(obtainState == true){
            stateToSend = "Activado"
        }
        else
        {
            stateToSend = "Desactivado"
        }
        let idUser = (defaults.objectForKey("userId") as? String)!
        let endpoint: String = "http://buserdevel.cfapps.io/user/preference/editState"
        let newTodo = ["userId": idUser,
                                     "preference":obtainPreferences,
                                     "state": stateToSend]
        Alamofire.request(.POST, endpoint, parameters: newTodo, encoding: .JSON)
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
        

        preference = sender.description
        print("Switch Value : \(sender.on)")
    }
    
   

    
    func createAlertPreferences(){
        //Crea el alert
        let alertController = UIAlertController(title: "Preferencias", message: "", preferredStyle: .Alert)
        //Accion para cerrar el alert
        let cancelAction = UIAlertAction(title: "Cerrar", style: UIAlertActionStyle.Cancel) {UIAlertAction in NSLog("Cerrar Presionado")}
        // Añade las acciones
        alertController.addAction(cancelAction)
        //Crear for para generarlos
        alertController.view.addSubview(createLabel(20.0,value2: 60.0, textLabel: "Notificaciones", tag:0))
        alertController.view.addSubview(createSwitch(200.0, value2: 60.0, tag:100))
        
        alertController.view.addSubview(createLabel(20.0,value2: 100.0, textLabel: "Correos", tag:1))
        alertController.view.addSubview(createSwitch(200.0, value2: 100.0, tag:101))
        // Dimensiones del alert view
        let height:NSLayoutConstraint = NSLayoutConstraint(item: alertController.view, attribute: NSLayoutAttribute.Height, relatedBy: NSLayoutRelation.Equal, toItem: nil, attribute: NSLayoutAttribute.NotAnAttribute, multiplier: 1, constant: self.view.frame.height * 0.50)
        alertController.view.addConstraint(height)
        //Presentar el alert view
        self.presentViewController(alertController, animated: true, completion: nil)
    }
  

}
