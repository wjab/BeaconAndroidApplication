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
//, FBSDKLoginButtonDelegate
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
        
        /*if (FBSDKAccessToken.currentAccessToken() != nil)
        {
            // User is already logged in, do work such as go to next view controller.
            
            // Or Show Logout Button
            let loginView : FBSDKLoginButton = FBSDKLoginButton()
            self.view.addSubview(loginView)
            loginView.center = self.view.center
            loginView.readPermissions = ["public_profile", "email", "user_friends"]
            loginView.delegate = self
            self.returnUserData()
        }
        else
        {
            let loginView : FBSDKLoginButton = FBSDKLoginButton()
            self.view.addSubview(loginView)
            loginView.center = self.view.center
            loginView.readPermissions = ["public_profile", "email", "user_friends"]
            loginView.delegate = self
        }
 */
    }
    // Facebook Delegate Methods
    
    /*func loginButton(loginButton: FBSDKLoginButton!, didCompleteWithResult result: FBSDKLoginManagerLoginResult!, error: NSError!) {
        print("User Logged In")
        
        if ((error) != nil)
        {
        print(error)
        }
        else if result.isCancelled {
            print("cancelado")
        }
        else {
            // If you ask for multiple permissions at once, you
            // should check if specific permissions missing
            if result.grantedPermissions.contains("email")
            {
                print("permmisos")
            }
            
            self.returnUserData()
        }
        
    }
    
    func loginButtonDidLogOut(loginButton: FBSDKLoginButton!) {
        print("User Logged Out")
    }
    
    func returnUserData()
    {
        let graphRequest : FBSDKGraphRequest = FBSDKGraphRequest(graphPath: "me", parameters: nil)
        graphRequest.startWithCompletionHandler({ (connection, result, error) -> Void in
            
            if ((error) != nil)
            {
                // Process error
                print("Error: \(error)")
            }
            else
            {
                print("fetched user: \(result)")
                let userName : NSString = result.valueForKey("name") as! NSString
                print("User Name is: \(userName)")
                let userEmail : NSString = result.valueForKey("email") as! NSString
                print("User Email is: \(userEmail)")
            }
        })
    }
    */
    func login(){
        let alertController = UIAlertController(title: "iOScreator", message:
            "Hello, world!", preferredStyle: UIAlertControllerStyle.Alert)
        alertController.addAction(UIAlertAction(title: "http://buserdevel.cfapps.io/user", style: UIAlertActionStyle.Default,handler: nil))
        
        self.presentViewController(alertController, animated: true, completion: nil)
        
    }
}

