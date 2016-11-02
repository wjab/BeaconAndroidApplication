//
//  ShopCell.swift
//  BeakonIOS
//
//  Created by Alejandra on 9/7/16.
//  Copyright © 2016 CentauroSolutions. All rights reserved.
//

import UIKit

class ShopCell: UITableViewCell {

    @IBOutlet weak var shopImage: UIImageView!
    @IBOutlet weak var scanImage: UIImageView!
    @IBOutlet weak var walkinImage: UIImageView!
    @IBOutlet weak var purchaseImage: UIImageView!
    @IBOutlet weak var walkinL: UILabel!
    @IBOutlet weak var scanL: UILabel!
    @IBOutlet weak var purchaseL: UILabel!
    var purchase = ""
    var walkin = ""
    var scan = ""
    internal func configure(purchase: String, walkin: String, scan: String, urlImageShop: String) {
        self.purchase = purchase
        self.scan = scan
        self.walkin = walkin
        
        purchaseL.text = "C/"+purchase
        scanL.text = scan
        walkinL.text = walkin
        
        let url = NSURL(string: urlImageShop)
        
        shopImage.hnk_setImageFromURL(url!, placeholder: nil, success: { (image) -> Void in
            self.shopImage.image = image
            }, failure: { (error) -> Void in
                self.shopImage.image = UIImage(named: "image_not_found")
                
        })
        
        
        self.validationImageToShow()
    }
    func validationImageToShow()
    {
       
        if(self.purchase == "0")
        {
            purchaseImage.image = UIImage(named:"purchase-gray")
            purchaseL.textColor = UIColor.grayColor()
        }
        else
        {
            purchaseImage.image = UIImage(named:"purchase-blue")
            purchaseL.textColor = UIColor(red: 14/255, green: 85/255, blue: 183/255, alpha: 1.0)
        }
        if(self.walkin == "0")
        {
            walkinImage.image = UIImage(named:"walk-in-gray")
            walkinL.textColor = UIColor.grayColor()
        }
        else
        {
            walkinImage.image = UIImage(named:"walk-in-blue")
            walkinL.textColor = UIColor(red: 14/255, green: 85/255, blue: 183/255, alpha: 1.0)
        }
        if(self.scan == "0")
        {
            scanImage.image = UIImage(named:"scan-gray")
            scanL.textColor = UIColor.grayColor()
        }
        else
        {
            scanImage.image = UIImage(named:"scan-blue")
            scanL.textColor = UIColor(red: 14/255, green: 85/255, blue: 183/255, alpha: 1.0)
        }
        
    }

}
