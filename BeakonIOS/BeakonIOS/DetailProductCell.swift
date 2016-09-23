//
//  DetailProductCell.swift
//  BeakonIOS
//
//  Created by Alejandra on 9/9/16.
//  Copyright © 2016 CentauroSolutions. All rights reserved.
//

import UIKit

class DetailProductCell: UICollectionViewCell {
    @IBOutlet weak var nameL: UILabel!
    @IBOutlet weak var isAddedImage: UIButton!
    @IBOutlet weak var productImage: UIImageView!
     var wishArray: [Wish] = []
    var product:Product = Product()
    let utilsC = UtilsC()
    
    internal func configure(name: String, urlImageProduct: String, product: Product) {
      //  nameL.text = name
        self.product = product
       utilsC.obtainWishListUser(product)
        productImage.image = NSURL(string: String(urlImageProduct)).flatMap { NSData(contentsOfURL: $0) }.flatMap { UIImage(data: $0) }!
        isAddedImage.addTarget(self, action: #selector(send), forControlEvents: .TouchUpInside)
        let gradientLayerView: UIView = UIView(frame: CGRectMake(0, 0, productImage.bounds.width, productImage.bounds.height))
        let gradient: CAGradientLayer = CAGradientLayer()
        gradient.frame = gradientLayerView.bounds
        gradient.colors = [
            UIColor.clearColor().CGColor,
            UIColor.clearColor().CGColor,
            UIColor.grayColor().CGColor
        ]
        gradientLayerView.layer.insertSublayer(gradient, atIndex: 0)
        self.productImage.layer.insertSublayer(gradientLayerView.layer, atIndex: 0)
        print(UtilsC.wishArray.count)
        self.wishArray = UtilsC.wishArray
    }
    func send(){
        utilsC.addWishList(self.product)
        print(self.product.productNamePropeties)
        //compare(self.product)
        NSNotificationCenter.defaultCenter().postNotificationName("load", object: nil)
       //self.tableView.reloadRowsAtIndexPaths(paths, withRowAnimation: UITableViewRowAnimation.None)
    }
    
    func compare(productReceive:Product){
        for (indexP, product) in self.wishArray.enumerate()
        {
            print(indexP)
            if(product.productIdPropeties==productReceive.productIdPropeties){
                if(product.isAddedPropeties == true){
                    self.isAddedImage.setImage(UIImage(named: "icon_add"), forState: UIControlState.Normal)
                }
                else{
                    self.isAddedImage.setImage(UIImage(named: "icon_added"), forState: UIControlState.Normal)
                }
            }
        }
    }
    
}
