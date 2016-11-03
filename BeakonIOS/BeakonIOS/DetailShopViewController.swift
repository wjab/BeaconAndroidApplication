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
    @IBOutlet weak var scanText: UILabel!
    @IBOutlet weak var walkText: UILabel!
    @IBOutlet weak var cityTL: UILabel!
    @IBOutlet weak var shopText: UILabel!
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
    var actualyArrayIndex = 0
    var utils = UtilsC()
    var wishCount = 1
     var btn1 = UIButton()
     let defaults = NSUserDefaults.standardUserDefaults()
     let button =  UIButton(type: .Custom)
    
    override func viewDidLoad()
    {
        super.viewDidLoad()
        self.navigationItem.title = ""
        
        cityTL.text = shop.cityPropeties
        addressTL.text = shop.adressPropeties
        purchaseTL.text = shop.totalGiftPointsPropeties.purchasePropeties
        walkinTL.text = shop.totalGiftPointsPropeties.walkinPropeties
        scanTL.text = shop.totalGiftPointsPropeties.scanPropeties
        
        let urlShop = NSURL(string: String(shop.imagePropeties))
        imageShop.hnk_setImageFromURL(urlShop!, placeholder: nil, success: { (image) -> Void in
            self.imageShop.image = image
            }, failure: { (error) -> Void in
                self.imageShop.image = UIImage(named: "image_not_found")
        })

        imageShopDetail.hnk_setImageFromURL(urlShop!, placeholder: nil, success: { (image) -> Void in
            self.imageShopDetail.image = image
            }, failure: { (error) -> Void in
                self.imageShopDetail.image = UIImage(named: "image_not_found")
        })
        validationImageToShow()
       
        wishCount = defaults.objectForKey("wishCount")as!Int
        let points = defaults.objectForKey("points") as! Int
        btn1 = Utils.loadWishListButton(btn1, wishCount: wishCount)
        btn1.addTarget(self, action: #selector(DetailShopViewController.openWishList), forControlEvents: .TouchUpInside)
        self.navigationItem.setRightBarButtonItem(UIBarButtonItem(customView: btn1), animated: true);
        
        // Crea el view con el label de puntos y el arrow de imagen
        let myView = Utils.createPointsView(points, activateEvents: true)
        let gesture = UITapGestureRecognizer(target : self, action: #selector(DetailShopViewController.clickOnButton))
        myView.addGestureRecognizer(gesture)
        
        self.navigationItem.titleView = myView
        
        navigationItem.backBarButtonItem = UIBarButtonItem(title: "", style: .Plain, target: nil, action: nil)
              NSNotificationCenter.defaultCenter().addObserver(self, selector: #selector(DetailShopViewController.refreshWishCount),name:"refreshWishCountDetailShop", object: nil)
        NSNotificationCenter.defaultCenter().addObserver(self, selector: #selector(DetailShopViewController.refreshPoints(_:)),name:"refreshPointsShop", object: nil)
    }
    
    func refreshPoints(notification: NSNotification){
        //load data here
        let defaults = NSUserDefaults.standardUserDefaults()
        let points = defaults.objectForKey("points") as! Int
        self.button.setTitle(String(points), forState: UIControlState.Normal)
    }
    
    func refreshWishCount(){
        //Refresca el contador
        self.wishCount = defaults.objectForKey("wishCount")as!Int
        btn1.setTitle(String(self.wishCount), forState: .Normal)
    }
    
    //Abre el historial de puntos
    func clickOnButton() {
        let secondViewController = self.storyboard?.instantiateViewControllerWithIdentifier("HistoryPointsViewController") as! HistoryPointsViewController
        self.navigationController?.pushViewController(secondViewController, animated: true)
    }
    //Abre la lista de deseos
    func openWishList(){
        let secondViewController = self.storyboard?.instantiateViewControllerWithIdentifier("WishViewController") as! WishViewController
        self.navigationController?.pushViewController(secondViewController, animated: true)
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    // MARK: UICollectionViewDataSource
    
    func numberOfSectionsInCollectionView(collectionView: UICollectionView) -> Int {
        // #warning Incomplete implementation, return the number of sections
        return 1
    }
    
    func collectionView(collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return self.shop.departments.count
    }
    
       func collectionView(collectionView: UICollectionView, cellForItemAtIndexPath indexPath: NSIndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCellWithReuseIdentifier(reuseIdentifier, forIndexPath: indexPath) as! DepartmentCell
        let departmentObject = self.shop.departments[indexPath.row]
         cell.configure(departmentObject, urlShop: shop.imagePropeties)
        return cell
    }
   
    func collectionView(collectionView: UICollectionView, didSelectItemAtIndexPath indexPath: NSIndexPath) {
        self.actualyArrayIndex = indexPath.row
        let secondViewController = self.storyboard?.instantiateViewControllerWithIdentifier("DetailDepartmentViewController") as! DetailDepartmentViewController
        secondViewController.department = self.shop.departmentsPropeties[self.actualyArrayIndex]
        secondViewController.shopId = self.shop.idPropeties
        self.navigationController?.pushViewController(secondViewController, animated: true)
        print("You selected cell #\(indexPath.item)!")
    }
    
    func validationImageToShow()
    {
        let purchase = shop.totalGiftPointsPropeties.purchasePropeties
        let walkin = shop.totalGiftPointsPropeties.walkinPropeties
        let scan = shop.totalGiftPointsPropeties.scanPropeties
        if(purchase == "0")
        {
            shopText.textColor = UIColor.grayColor()
            imagePurchase.image = UIImage(named:"purchase-gray")
            purchaseTL.textColor = UIColor.grayColor()
        }
        else
        {
            shopText.textColor = Constants.colors.getDarkBlue()
            imagePurchase.image = UIImage(named:"purchase-blue")
            purchaseTL.textColor = Constants.colors.getDarkBlue()
        }
        if(walkin == "0")
        {
            walkText.textColor = UIColor.grayColor()
            imageWalkin.image = UIImage(named:"walk-in-gray")
            walkinTL.textColor = UIColor.grayColor()
        }
        else
        {
            walkText.textColor = Constants.colors.getDarkBlue()
            imageWalkin.image = UIImage(named:"walk-in-blue")
            walkinTL.textColor = Constants.colors.getDarkBlue()
        }
        if(scan == "0")
        {
            scanText.textColor = UIColor.grayColor()
            imageScan.image = UIImage(named:"scan-gray")
            scanTL.textColor = UIColor.grayColor()
        }
        else
        {
            scanText.textColor = Constants.colors.getDarkBlue()
            imageScan.image = UIImage(named:"scan-blue")
            scanTL.textColor = Constants.colors.getDarkBlue()
        }

    }
    
}
