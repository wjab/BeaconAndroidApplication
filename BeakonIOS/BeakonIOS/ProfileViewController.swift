//
//  ProfileViewController.swift
//  BeakonIOS
//
//  Created by Alejandra on 8/25/16.
//  Copyright © 2016 CentauroSolutions. All rights reserved.
//

import UIKit

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
    
    @IBOutlet weak var numberTF: UITextField!
    @IBOutlet weak var emailTF: UITextField!
    @IBOutlet weak var lastnameTF: UITextField!
    @IBOutlet weak var nameTF: UITextField!
    @IBOutlet weak var dateTF: UITextField!
    @IBOutlet weak var facebookBtn: UIButton!
     var btn1 = UIButton()
     let defaults = NSUserDefaults.standardUserDefaults()
    var typerUser = ""
    var wishCount = 1
    var idFacebook = ""
    override func viewDidLoad() {
        super.viewDidLoad()
        self.navigationItem.title = ""
        nameTF.text = defaults.objectForKey("name") as? String
        lastnameTF.text = defaults.objectForKey("lastname") as? String
        emailTF.text = defaults.objectForKey("email") as? String
        self.typerUser = defaults.objectForKey("socialNetworkType") as! String
        self.validateStateUser()
        self.wishCount = defaults.objectForKey("wishCount")as!Int
        let points = defaults.objectForKey("points") as! Int
        //Button abre  menu
        //var open = UIButton()
        //let image = defaults.objectForKey("image")as! String
        //let typeUser = defaults.objectForKey("socialNetworkType")as! String
        //open = Utils.loadMenuButton(open, image: image, typeUser: typeUser)
        //open.frame = CGRectMake(0, 0, 40, 35)
        //open.layer.masksToBounds = false
        //open.layer.cornerRadius = open.frame.height/2
        //open.clipsToBounds = true
        //open.addTarget(self, action: #selector(ProfileViewController.openMenu), forControlEvents: .TouchUpInside)
        //self.navigationItem.setLeftBarButtonItem(UIBarButtonItem(customView: open), animated: true)
        
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
            facebookBtn.addTarget(self, action: #selector(ProfileViewController.openFacebookProfile), forControlEvents: UIControlEvents.TouchUpInside)
        }
        else
        {
             facebookBtn.setTitle("Actualizar información", forState: .Normal)
        }
        
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
            dateTF.userInteractionEnabled = false
        }
        else
        {
            emailTF.userInteractionEnabled = false
            numberTF.userInteractionEnabled = false
            dateTF.userInteractionEnabled = false
            nameTF.userInteractionEnabled = false
            lastnameTF.userInteractionEnabled = false
            
        }
    }

}
