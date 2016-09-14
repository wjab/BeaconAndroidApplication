//
//  DetailProductViewController.swift
//  BeakonIOS
//
//  Created by Alejandra on 9/9/16.
//  Copyright © 2016 CentauroSolutions. All rights reserved.
//

import UIKit

class DetailProductViewController: UIViewController {
    var product:Product = Product()
    @IBOutlet weak var productImage: UIImageView!
    override func viewDidLoad() {
        super.viewDidLoad()
        productImage.image = NSURL(string: String(product.imageUrlListPropeties[0])).flatMap { NSData(contentsOfURL: $0) }.flatMap { UIImage(data: $0) }!

    }

    override func didReceiveMemoryWarning() {
        
    }
}
