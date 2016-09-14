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
        
       purchaseL.text = purchase
        scanL.text = scan
        walkinL.text = walkin
        shopImage.image = NSURL(string: String(urlImageShop)).flatMap { NSData(contentsOfURL: $0) }.flatMap { UIImage(data: $0) }!
        self.validationImageToShow()
    }
    func validationImageToShow()
    {
       
        if(self.purchase == "0")
        {
            purchaseImage.image = UIImage(named:"purchase-gray")
        }
        else
        {
            purchaseImage.image = UIImage(named:"purchase-blue")
        }
        if(self.walkin == "0")
        {
            walkinImage.image = UIImage(named:"walk-in-gray")
        }
        else
        {
            walkinImage.image = UIImage(named:"walk-in-blue")
        }
        if(self.scan == "0")
        {
            scanImage.image = UIImage(named:"scan-gray")
        }
        else
        {
            scanImage.image = UIImage(named:"scan-blue")
        }
        
    }

}
