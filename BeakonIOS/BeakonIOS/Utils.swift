//
//  Utils.swift
//  BeakonIOS
//
//  Created by Eduardo Trejos on 10/20/16.
//  Copyright © 2016 CentauroSolutions. All rights reserved.
//

import Foundation
import AssetsLibrary

class Utils{

 static func loadMenuButton(button: UIButton, image: String, typeUser: String) -> UIButton{
        let open = button
        let url = NSURL(string: image)
        open.frame = CGRectMake(0, 0, 40, 35)
        open.layer.masksToBounds = false
        open.layer.cornerRadius = open.frame.height/2
        open.clipsToBounds = true
    
        open.hnk_setImageFromURL(url!, placeholder: nil, success: { (image) -> Void in
            open.setBackgroundImage(image, forState: UIControlState.Normal)
            }, failure: { (error) -> Void in
            open.setBackgroundImage(UIImage(named: "profiledefault"), forState: UIControlState.Normal )
            
        })


    
        return open
    }
}