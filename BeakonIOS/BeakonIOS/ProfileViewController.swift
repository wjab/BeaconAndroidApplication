//
//  ProfileViewController.swift
//  BeakonIOS
//
//  Created by Alejandra on 8/25/16.
//  Copyright © 2016 CentauroSolutions. All rights reserved.
//

import UIKit

class ProfileViewController: UIViewController {
    
    @IBOutlet weak var numberTF: UITextField!
    @IBOutlet weak var emailTF: UITextField!
    @IBOutlet weak var lastnameTF: UITextField!
    @IBOutlet weak var nameTF: UITextField!
    @IBOutlet weak var dateTF: UITextField!
    var typerUser = ""
    override func viewDidLoad() {
        super.viewDidLoad()
       // addSlideMenuButton()
        let defaults = NSUserDefaults.standardUserDefaults()
        nameTF.text = defaults.objectForKey("name") as? String
        lastnameTF.text = defaults.objectForKey("lastname") as? String
        emailTF.text = defaults.objectForKey("email") as? String
        self.typerUser = defaults.objectForKey("socialNetworkType") as! String
        self.validateStateUser()
        let points = defaults.objectForKey("points") as! Int
        //Button abre  menu
        var open = UIButton()
        let image = defaults.objectForKey("image")as! String
        let typeUser = defaults.objectForKey("socialNetworkType")as! String
        open = Utils.loadMenuButton(open, image: image, typeUser: typeUser)
        open.frame = CGRectMake(0, 0, 40, 35)
        open.layer.masksToBounds = false
        open.layer.cornerRadius = open.frame.height/2
        open.clipsToBounds = true
        open.addTarget(self, action: #selector(ProfileViewController.openMenu), forControlEvents: .TouchUpInside)
        self.navigationItem.setLeftBarButtonItem(UIBarButtonItem(customView: open), animated: true)
        //Genera el boton de la derecha que contiene el corazon que abre la lista de deseos
        let btn1 = UIButton()
        btn1.setImage(UIImage(named: "icon_added"), forState: .Normal)
        btn1.frame = CGRectMake(0, 0, 30, 25)
        btn1.addTarget(self, action: #selector(ProfileViewController.openWishList), forControlEvents: .TouchUpInside)
        self.navigationItem.setRightBarButtonItem(UIBarButtonItem(customView: btn1), animated: true);
        //Genera el boton del centro que contiene los puntos del usuario
        let button =  UIButton(type: .Custom)
        button.frame = CGRectMake(0, 0, 100, 40) as CGRect
        button.setTitle(String(points), forState: UIControlState.Normal)
        button.addTarget(self, action: #selector(ProfileViewController.clickOnButton(_:)), forControlEvents: UIControlEvents.TouchUpInside)
        self.navigationItem.titleView = button
        
    }
    
    //Abre el menu
    func openMenu(){
        let secondViewController = self.storyboard?.instantiateViewControllerWithIdentifier("MenuContainerViewController") as! MenuContainerViewController
        self.navigationController?.pushViewController(secondViewController, animated: true)
    }
    //Abre el historial de puntos
    func clickOnButton(button: UIButton) {
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
