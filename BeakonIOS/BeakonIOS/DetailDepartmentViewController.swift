//
//  DetailDepartmentViewController.swift
//  BeakonIOS
//
//  Created by Christopher on 9/14/16.
//  Copyright © 2016 CentauroSolutions. All rights reserved.
//

import UIKit

class DetailDepartmentViewController: UIViewController {
    var department : Department!
    private let reuseIdentifier = "productDepartmentCell"
    @IBOutlet weak var collection: UICollectionView!
    var actualyArrayIndex = 0
    @IBOutlet weak var departmentImage: UIImageView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        departmentImage.image =  NSURL(string: String(department.departmentUrlPropeties)).flatMap { NSData(contentsOfURL: $0) }.flatMap { UIImage(data: $0) }!
        let defaults = NSUserDefaults.standardUserDefaults()
        let points = defaults.objectForKey("points") as! Int
        let btn1 = UIButton()
        btn1.setImage(UIImage(named: "icon_added"), forState: .Normal)
        btn1.frame = CGRectMake(0, 0, 30, 25)
        btn1.addTarget(self, action: #selector(DetailDepartmentViewController.openWishList), forControlEvents: .TouchUpInside)
        self.navigationItem.setRightBarButtonItem(UIBarButtonItem(customView: btn1), animated: true);
        //Genera el boton del centro que contiene los puntos del usuario
        let button =  UIButton(type: .Custom)
        button.frame = CGRectMake(0, 0, 100, 40) as CGRect
        button.setTitle(String(points), forState: UIControlState.Normal)
        button.addTarget(self, action: #selector(DetailDepartmentViewController.clickOnButton(_:)), forControlEvents: UIControlEvents.TouchUpInside)
        self.navigationItem.titleView = button
        
    }
    
    //Abre el historial de puntos
    func clickOnButton(button: UIButton) {
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
        //3
        return 1
    }
    
    
    func collectionView(collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of items
        //4
        print(self.department.products.count)
        return self.department.products.count
    }
    
    func collectionView(collectionView: UICollectionView, cellForItemAtIndexPath indexPath: NSIndexPath) -> UICollectionViewCell {
        //5
         let cell = collectionView.dequeueReusableCellWithReuseIdentifier(reuseIdentifier, forIndexPath: indexPath) as! DetailProductDepartmentCell
        
        let productObject = self.department.products[indexPath.row]

       cell.configure(productObject.productNamePropeties, urlImageProduct: productObject.imageUrlListPropeties[0])
        return cell
    }
    
    
    func collectionView(collectionView: UICollectionView, didSelectItemAtIndexPath indexPath: NSIndexPath) {
        self.actualyArrayIndex = indexPath.row
        let secondViewController = self.storyboard?.instantiateViewControllerWithIdentifier("DetailProductViewController") as! DetailProductViewController
        secondViewController.product = self.department.productsPropeties[self.actualyArrayIndex]
        self.navigationController?.pushViewController(secondViewController, animated: true)
        print("You selected cell #\(indexPath.item)!")
    }

}
