//
//  WishCell.swift
//  BeakonIOS
//
//  Created by Christopher on 9/12/16.
//  Copyright © 2016 CentauroSolutions. All rights reserved.
//

import UIKit

class WishCell: UITableViewCell {
    @IBOutlet weak var productImage: UIImageView!
    @IBOutlet weak var nameL: UILabel!
    internal func configure(name: String, urlImage: String) {
        nameL.text = name
        productImage.image = NSURL(string: String(urlImage)).flatMap { NSData(contentsOfURL: $0) }.flatMap { UIImage(data: $0) }!
       
    }
}
