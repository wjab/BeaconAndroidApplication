//
//  PromoCell.swift
//  BeakonIOS
//
//  Created by Alejandra on 9/7/16.
//  Copyright © 2016 CentauroSolutions. All rights reserved.
//

import UIKit
import Haneke



class PromoCell: UITableViewCell {
    @IBOutlet weak var promoImage: UIImageView!
    @IBOutlet weak var nameL: UILabel!
    @IBOutlet weak var pointsL: UILabel!
    
    internal func configure(name: String, urlImagePromo: String, points: String)
    {
        nameL.text = name
        
        let url = NSURL(string: urlImagePromo)
        promoImage.hnk_setImageFromURL(url!, placeholder: nil, success: { (image) -> Void in
            self.promoImage.image = image
            }, failure: { (error) -> Void in
                self.promoImage.image = UIImage(named: "image_not_found")
                
        })
        
        pointsL.text = points
        let gradientLayerView: UIView = UIView(frame: CGRectMake(0, 0, promoImage.bounds.width, promoImage.bounds.height))
        let gradient: CAGradientLayer = CAGradientLayer()
        gradient.frame = gradientLayerView.bounds
        gradient.colors = [
            UIColor.clearColor().CGColor,
              UIColor.clearColor().CGColor,
            UIColor.grayColor().CGColor
        ]
      
        gradientLayerView.layer.insertSublayer(gradient, atIndex: 0)
       self.promoImage.layer.insertSublayer(gradientLayerView.layer, atIndex: 0)
}
}
