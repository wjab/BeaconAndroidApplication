//
//  ViewController.swift
//  BeakonIOS
//
//  Created by Christopher on 8/23/16.
//  Copyright © 2016 CentauroSolutions. All rights reserved.
//

import UIKit
import Alamofire
import SwiftyJSON
class ViewController: UIViewController {
let recognizer = UITapGestureRecognizer()
let url = "http://buserdevel.cfapps.io/user"
@IBOutlet weak var imageViewFacebook: UIImageView!
    let defaults = NSUserDefaults.standardUserDefaults()
    override func viewDidLoad()
    {
        super.viewDidLoad()
        imageViewFacebook.userInteractionEnabled = true
        recognizer.addTarget(self, action: #selector(ViewController.login))
        imageViewFacebook.addGestureRecognizer(recognizer)
       
    }
    func login(){
        let alertController = UIAlertController(title: "iOScreator", message:
            "Hello, world!", preferredStyle: UIAlertControllerStyle.Alert)
        alertController.addAction(UIAlertAction(title: "http://buserdevel.cfapps.io/user", style: UIAlertActionStyle.Default,handler: nil))
        
        self.presentViewController(alertController, animated: true, completion: nil)
        
    }
    
}

