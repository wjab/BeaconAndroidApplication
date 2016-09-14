//
//  DetailShopViewController.swift
//  BeakonIOS
//
//  Created by Alejandra on 9/2/16.
//  Copyright © 2016 CentauroSolutions. All rights reserved.
//

import UIKit
import Alamofire
import SwiftyJSON
class DetailShopViewController: UIViewController, UICollectionViewDataSource, UICollectionViewDelegate {
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
    private let reuseIdentifier = "departmentCell"
    @IBOutlet weak var collection: UICollectionView!

    override func viewDidLoad() {
        super.viewDidLoad()
        cityTL.text = shop.cityPropeties
        addressTL.text = shop.adressPropeties
        purchaseTL.text = shop.totalGiftPointsPropeties.purchasePropeties
        walkinTL.text = shop.totalGiftPointsPropeties.walkinPropeties
        scanTL.text = shop.totalGiftPointsPropeties.scanPropeties
        imageShop.image = NSURL(string: String(shop.imagePropeties)).flatMap { NSData(contentsOfURL: $0) }.flatMap { UIImage(data: $0) }!
        imageShopDetail.image = NSURL(string: String(shop.imagePropeties)).flatMap { NSData(contentsOfURL: $0) }.flatMap { UIImage(data: $0) }!
        validationImageToShow()
        // Do any additional setup after loading the view.
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    
    // MARK: UICollectionViewDataSource
    
    func numberOfSectionsInCollectionView(collectionView: UICollectionView) -> Int {
        // #warning Incomplete implementation, return the number of sections
        //3
        return 1
    }
    
    
    func collectionView(collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of items
        //4
        print(self.shop.departments.count)
        return self.shop.departments.count
    }
    
    func collectionView(collectionView: UICollectionView, cellForItemAtIndexPath indexPath: NSIndexPath) -> UICollectionViewCell {
        //5
        let cell:UICollectionViewCell = self.collection.dequeueReusableCellWithReuseIdentifier(reuseIdentifier,forIndexPath: indexPath) as UICollectionViewCell!
        
        let departmentObject = self.shop.departments[indexPath.row]
        let imageView = UIImageView(frame: CGRectMake(0, 0, 160, 100))
        let image = NSURL(string: String(departmentObject.departmentUrl)).flatMap { NSData(contentsOfURL: $0) }.flatMap { UIImage(data: $0) }!

        imageView.image = image
        cell.backgroundView = UIView()
        cell.backgroundView!.addSubview(imageView)
        
        // cell.imageView = NSURL(string: String(departmentObject.departmentUrl)).flatMap { NSData(contentsOfURL: $0) }.flatMap { UIImage(data: $0) }!
        
        return cell
    }
    
    
    func collectionView(collectionView: UICollectionView, didSelectItemAtIndexPath indexPath: NSIndexPath) {
        // handle tap events
        print("You selected cell #\(indexPath.item)!")
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
