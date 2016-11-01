//
//  DepartmentCell.swift
//  BeakonIOS
//
//  Created by Christopher on 10/31/16.
//  Copyright © 2016 CentauroSolutions. All rights reserved.
//

import UIKit
import Haneke

class DepartmentCell: UICollectionViewCell {
    @IBOutlet weak var departmentImage: UIImageView!
    @IBOutlet weak var nameL: UILabel!
    @IBOutlet weak var shopImage: UIImageView!
    
    internal func configure(department:Department, urlShop:String)
    {
        let url = NSURL(string: department.departmentUrlPropeties)
        departmentImage.hnk_setImageFromURL(url!, placeholder: nil, success: { (image) -> Void in
            self.departmentImage.image = image
            }, failure: { (error) -> Void in
                self.departmentImage.image = UIImage(named: "image_not_found")
            })
        let urlShop = NSURL(string: urlShop)
        shopImage.hnk_setImageFromURL(urlShop!, placeholder: nil, success: { (image) -> Void in
            self.shopImage.image = image
            }, failure: { (error) -> Void in
                self.shopImage.image = UIImage(named: "image_not_found")
        })
        nameL.text = department.namePropeties
        let gradientLayerView: UIView = UIView(frame: CGRectMake(0, 0, departmentImage.bounds.width, departmentImage.bounds.height))
        let gradient: CAGradientLayer = CAGradientLayer()
        gradient.frame = gradientLayerView.bounds
        gradient.colors = [
            UIColor.clearColor().CGColor,
            UIColor.clearColor().CGColor,
            UIColor.grayColor().CGColor
        ]
        gradientLayerView.layer.insertSublayer(gradient, atIndex: 0)
        self.departmentImage.layer.insertSublayer(gradientLayerView.layer, atIndex: 0)
    }

}
