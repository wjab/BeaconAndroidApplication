//
//  HeaderMenuViewController.swift
//  BeakonIOS
//
//  Created by Alejandra on 9/21/16.
//  Copyright © 2016 CentauroSolutions. All rights reserved.
//

import UIKit
import Alamofire
import SwiftyJSON

class HeaderMenuViewController: UIViewController , UIImagePickerControllerDelegate,UINavigationControllerDelegate {

    @IBOutlet weak var profileImage: UIImageView!
    let imagePicker = UIImagePickerController()
    var url: String!
    let defaults = NSUserDefaults.standardUserDefaults()
    
    override func viewDidLoad() {
        super.viewDidLoad()
           imagePicker.delegate = self
        let singleTap = UITapGestureRecognizer(target: self, action:#selector(HeaderMenuViewController.tapDetected))
        singleTap.numberOfTapsRequired = 1
         profileImage.userInteractionEnabled = true
        profileImage.addGestureRecognizer(singleTap)
        profileImage.layer.borderWidth=1.0
        profileImage.layer.masksToBounds = false
        profileImage.layer.borderColor = UIColor.whiteColor().CGColor
        profileImage.layer.cornerRadius = 13
        profileImage.layer.cornerRadius =   profileImage.frame.size.height/2
        profileImage.clipsToBounds = true
        self.loadImageFromPath("assets-library://asset/asset.JPG?id=ED7AC36B-A150-4C38-BB8C-B6D696F4F2ED&ext=JPG")
        //assets-library://asset/asset.JPG?id=9F983DBA-EC35-42B8-8773-B597CF782EDD&ext=JPG
        // Do any additional setup after loading the view.
    }
    
    func loadImageFromPath(path: String) {
        let image = UIImage(contentsOfFile: path)
        
        if image == nil {
            print("missing image at: \(path)")
        }
        print("Loading image from path: \(path)") // this is just for you to see the path in case you want to go to the directory, using Finder.
        profileImage.image = image
        }
    
    func tapDetected() {
        imagePicker.allowsEditing = false
        imagePicker.sourceType = .PhotoLibrary
        
        presentViewController(imagePicker, animated: true, completion: nil)
    }
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func imagePickerController(picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [String : AnyObject]) {
        if let pickedImage = info[UIImagePickerControllerOriginalImage] as? UIImage {
            profileImage.contentMode = .ScaleAspectFit
            //self.url = String(info[UIImagePickerControllerReferenceURL]!)
            //updateImageProfileService(self.url!)
           profileImage.image = pickedImage
            
            
            let imageURL = info[UIImagePickerControllerReferenceURL] as! NSURL
            let imagePath =  imageURL.path!
            let localPath = NSURL(fileURLWithPath: NSTemporaryDirectory()).URLByAppendingPathComponent(imagePath)
            
            //this block of code adds data to the above path
            let path = localPath.relativePath!
            let imageName = info[UIImagePickerControllerOriginalImage] as! UIImage
            let data = UIImagePNGRepresentation(imageName)
            data?.writeToFile(imagePath, atomically: true)
            let printUrl = info[UIImagePickerControllerReferenceURL]
            //this block grabs the NSURL so you can use it in CKASSET
            let photoURL = NSURL(fileURLWithPath: path)
            print("---------------------------------------------------------")
            print(printUrl?.absoluteString)
        }
        
        dismissViewControllerAnimated(true, completion: nil)
    }
    
    func imagePickerControllerDidCancel(picker: UIImagePickerController) {
        dismissViewControllerAnimated(true, completion: nil)
    }
   
    func updateImageProfileService(pathImage:String){
        //userService/user/editPathImage/userId
        //PUT
        let idUser = (defaults.objectForKey("userId") as? String)!
        let endpoint: String = "http://buserdevel.cfapps.io/user/editPathImage/"+idUser
        let newTodo = ["pathImage": pathImage]
        print(url)
        Alamofire.request(.PUT, endpoint, parameters: newTodo, encoding: .JSON)
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
    }
}
