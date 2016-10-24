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
        var asset = ALAssetsLibrary()
        if (typeUser != "localuser"){
            open.setBackgroundImage(NSURL(string: String(image)).flatMap { NSData(contentsOfURL: $0) }.flatMap { UIImage(data: $0) }!, forState: UIControlState.Normal)
        }
        else
        {
            let fileUrl = NSURL(string:  image)
            asset.assetForURL(fileUrl, resultBlock: { asset in
                if let ast = asset {
                    let assetRep = ast.defaultRepresentation()
                    let iref = assetRep.fullResolutionImage().takeUnretainedValue()
                    let image = UIImage(CGImage: iref)
                    dispatch_async(dispatch_get_main_queue(), {
                        open.setBackgroundImage(image, forState: .Normal)
                    })
                }
                }, failureBlock: { error in
                    print("Error: \(error)")
            })
        }
        open.frame = CGRectMake(0, 0, 40, 35)
        open.layer.masksToBounds = false
        open.layer.cornerRadius = open.frame.height/2
        open.clipsToBounds = true
        
        
        return open
    }
}