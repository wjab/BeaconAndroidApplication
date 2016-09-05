//
//  ProfileViewController.swift
//  BeakonIOS
//
//  Created by Christopher on 8/25/16.
//  Copyright © 2016 CentauroSolutions. All rights reserved.
//

import UIKit

class ProfileViewController: BaseViewController {
    
    @IBOutlet weak var numberTF: UITextField!
    @IBOutlet weak var emailTF: UITextField!
    @IBOutlet weak var lastnameTF: UITextField!
    @IBOutlet weak var nameTF: UITextField!
    @IBOutlet weak var dateTF: UITextField!
    var typerUser = ""
    override func viewDidLoad() {
        super.viewDidLoad()
        addSlideMenuButton()
        let defaults = NSUserDefaults.standardUserDefaults()
        nameTF.text = defaults.objectForKey("name") as? String
        lastnameTF.text = defaults.objectForKey("lastname") as? String
        emailTF.text = defaults.objectForKey("email") as? String
        self.typerUser = defaults.objectForKey("socialNetworkType") as! String
        self.validateStateUser()
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
