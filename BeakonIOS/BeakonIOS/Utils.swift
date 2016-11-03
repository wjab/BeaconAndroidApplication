//
//  Utils.swift
//  BeakonIOS
//
//  Created by Eduardo Trejos on 10/20/16.
//  Copyright © 2016 CentauroSolutions. All rights reserved.
//

import Foundation
import AssetsLibrary

class Utils
{

 static func loadMenuButton(button: UIButton, image: String, typeUser: String) -> UIButton
 {
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
    
    static func loadWishListButton(btn1: UIButton, wishCount:Int)-> UIButton
    {
        let open = btn1
        btn1.setBackgroundImage(UIImage(named: "icon_added"), forState: .Normal)
        btn1.setTitle(String(wishCount), forState: .Normal)
        btn1.setTitleColor(UIColor(red:0.20, green:0.60, blue:0.40, alpha:1.0), forState: .Normal)
        btn1.frame = CGRectMake(0, 0, 30, 25)
        return open
    }
    
    static func createPointsView(points : Int, activateEvents : Bool) -> UIView
    {
        let customView = UIView()
        customView.frame = CGRectMake(0, 0, 50, 18) as CGRect
        
        //Genera el boton del centro que contiene los puntos del usuario
        let button =  UIButton(type: .Custom)
        button.frame = CGRectMake(0, -5, 50, 14) as CGRect
        button.setTitle(String(points), forState: UIControlState.Normal)
        button.enabled = false
        
        //Genera el boton del centro que contiene la imagen debajo del boton de los puntos
        let btn_points_img =  UIButton(type: .Custom)
        btn_points_img.frame = CGRectMake(16, 5, 18, 18) as CGRect
        btn_points_img.setImage(UIImage(named: "arrow"), forState: UIControlState.Normal)
        btn_points_img.enabled = false
        btn_points_img.adjustsImageWhenDisabled = false
        
        customView.addSubview(btn_points_img);
        customView.addSubview(button);
        
        return customView
    }
    
}