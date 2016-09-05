//
//  DetailShopViewController.swift
//  BeakonIOS
//
//  Created by Christopher on 9/2/16.
//  Copyright © 2016 CentauroSolutions. All rights reserved.
//

import UIKit
import Haneke
import Alamofire
import SwiftyJSON
class DetailShopViewController: UIViewController {
    var shop : Shop!
    @IBOutlet weak var cityTL: UILabel!
    @IBOutlet weak var imageShopDetail: UIImageView!
    @IBOutlet weak var imageShop: UIImageView!
    @IBOutlet weak var addressTL: UILabel!
    @IBOutlet weak var purchaseTL: UILabel!
    @IBOutlet weak var walkinTL: UILabel!
    @IBOutlet weak var scanTL: UILabel!
    @IBOutlet weak var imageScan: UIImageView!
    @IBOutlet weak var imageWalkin: UIImageView!
    @IBOutlet weak var imagePurchase: UIImageView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        cityTL.text = shop.cityPropeties
        addressTL.text = shop.adressPropeties
        purchaseTL.text = shop.totalGiftPointsPropeties.purchasePropeties
        walkinTL.text = shop.totalGiftPointsPropeties.walkinPropeties
        scanTL.text = shop.totalGiftPointsPropeties.scanPropeties
        let url = NSURL(string: String(shop.imagePropeties))!
        imageShop.hnk_setImageFromURL(url, format: Format<UIImage>(name: "original"))
        imageShopDetail.hnk_setImageFromURL(url, format: Format<UIImage>(name: "original"))
        validationImageToShow()
        // Do any additional setup after loading the view.
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func validationImageToShow()
    {
        let purchase = shop.totalGiftPointsPropeties.purchasePropeties
        let walkin = shop.totalGiftPointsPropeties.walkinPropeties
        let scan = shop.totalGiftPointsPropeties.scanPropeties
        if(purchase == "0")
        {
            imagePurchase.image = UIImage(named:"purchase-gray")
        }
        else
        {
            imagePurchase.image = UIImage(named:"purchase-blue")
        }
        if(walkin == "0")
        {
            imageWalkin.image = UIImage(named:"walk-in-gray")
        }
        else
        {
            imageWalkin.image = UIImage(named:"walk-in-blue")
        }
        if(scan == "0")
        {
            imageScan.image = UIImage(named:"scan-gray")
        }
        else
        {
            imageScan.image = UIImage(named:"scan-blue")
        }

    }
}
