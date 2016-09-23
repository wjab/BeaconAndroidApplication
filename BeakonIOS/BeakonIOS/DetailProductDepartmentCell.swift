//
//  DetailProductDepartmentCell.swift
//  BeakonIOS
//
//  Created by Alejandra on 9/14/16.
//  Copyright © 2016 CentauroSolutions. All rights reserved.
//

import UIKit

class DetailProductDepartmentCell: UICollectionViewCell {
    @IBOutlet weak var nameL: UILabel!
   // @IBOutlet weak var isAddedImage: UIButton!
    @IBOutlet weak var productImage: UIImageView!
    var product:Product = Product()
    internal func configure(name: String, urlImageProduct: String) {
        //  nameL.text = name
       // self.product = product
        productImage.image = NSURL(string: String(urlImageProduct)).flatMap { NSData(contentsOfURL: $0) }.flatMap { UIImage(data: $0) }!
        // isAddedImage.addTarget(self, action: #selector(send), forControlEvents: .TouchUpInside)
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
    }
    func send(){
        //  let utilsC = UtilsC()
        //utilsC.addWishList(self.product)
        print(self.product.productNamePropeties)
        //self.tableView.reloadRowsAtIndexPaths(paths, withRowAnimation: UITableViewRowAnimation.None)
    }
}
