//
//  ProductCell.swift
//  BeakonIOS
//
//  Created by Alejandra on 9/7/16.
//  Copyright © 2016 CentauroSolutions. All rights reserved.
//

import UIKit

class ProductCell: UITableViewCell {
    @IBOutlet weak var categoryImage: UIImageView!
    @IBOutlet weak var nameL: UILabel!
    internal func configure(name: String, urlImageCategory: String) {
        
        var image: UIImage? = NSURL(string: String(urlImageCategory)).flatMap { NSData(contentsOfURL: $0) }.flatMap { UIImage(data: $0) }!
        
        
        nameL.text = name
        categoryImage.image = image
        let gradientLayerView: UIView = UIView(frame: CGRectMake(0, 0, categoryImage.bounds.width, categoryImage.bounds.height))
        let gradient: CAGradientLayer = CAGradientLayer()
        gradient.frame = gradientLayerView.bounds
        gradient.colors = [
            UIColor.clearColor().CGColor,
            UIColor.clearColor().CGColor,
            UIColor.grayColor().CGColor
        ]
        gradientLayerView.layer.insertSublayer(gradient, atIndex: 0)
        self.categoryImage.layer.insertSublayer(gradientLayerView.layer, atIndex: 0)
    }
}

