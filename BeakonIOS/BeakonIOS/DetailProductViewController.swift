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
        let btn1 = UIButton()
        btn1.setImage(UIImage(named: "icon_add"), forState: .Normal)
        btn1.frame = CGRectMake(0, 0, 30, 25)
        btn1.addTarget(self, action: #selector(HomeTabViewController.openWishList), forControlEvents: .TouchUpInside)
        self.navigationItem.setRightBarButtonItem(UIBarButtonItem(customView: btn1), animated: true);

    }

    override func didReceiveMemoryWarning() {
        
    }
}
