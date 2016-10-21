//
//  Utils.swift
//  BeakonIOS
//
//  Created by Eduardo Trejos on 10/20/16.
//  Copyright © 2016 CentauroSolutions. All rights reserved.
//

import Foundation


class Utils{
    static func loadMenuButton(button: UIButton, image: String, typeUser: String) -> UIButton{
        let open = button

        if (typeUser != "localuser"){
            open.setBackgroundImage(NSURL(string: String(image)).flatMap { NSData(contentsOfURL: $0) }.flatMap { UIImage(data: $0) }!, forState: UIControlState.Normal)
        }
        else
        {
            if NSFileManager.defaultManager().fileExistsAtPath(image) {
                let url = NSURL(string: image)
                let data = NSData(contentsOfURL: url!)
                open.setBackgroundImage(UIImage(data: data!), forState: .Normal)
            }
            else{
                open.setBackgroundImage(UIImage(named: "profiledefault"), forState: .Normal)
            }
        }
        open.frame = CGRectMake(0, 0, 40, 35)
        open.layer.masksToBounds = false
        open.layer.cornerRadius = open.frame.height/2
        open.clipsToBounds = true
        
        
        return open
    }
}