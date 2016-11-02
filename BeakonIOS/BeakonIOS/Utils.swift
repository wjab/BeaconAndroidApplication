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
    
    static func loadWishListButton(btn1: UIButton, wishCount:Int)-> UIButton{
        let open = btn1
        btn1.setBackgroundImage(UIImage(named: "icon_added"), forState: .Normal)
        btn1.setTitle(String(wishCount), forState: .Normal)
        btn1.setTitleColor(UIColor(red:0.20, green:0.60, blue:0.40, alpha:1.0), forState: .Normal)
        btn1.frame = CGRectMake(0, 0, 30, 25)
        return open
    }
    
}