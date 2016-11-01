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
    var wishCount = 1
    @IBOutlet weak var departmentImage: UIImageView!
    let defaults = NSUserDefaults.standardUserDefaults()
    let btn1 = UIButton()
    override func viewDidLoad()
    {
        super.viewDidLoad()
        self.navigationItem.title = ""
        
        navigationItem.backBarButtonItem = UIBarButtonItem(title: "", style: .Plain, target: nil, action: nil)
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
        
        let urlDepartmentImage = NSURL(string: String(department.departmentUrlPropeties))
        departmentImage.hnk_setImageFromURL(urlDepartmentImage!, placeholder: nil, success: { (image) -> Void in
            self.departmentImage.image = image
            }, failure: { (error) -> Void in
                self.departmentImage.image = UIImage(named: "image_not_found")
                
        })

        wishCount = defaults.objectForKey("wishCount")as!Int
        let points = defaults.objectForKey("points") as! Int
       
        btn1.setBackgroundImage(UIImage(named: "icon_added"), forState: .Normal)
        btn1.setTitle(String(wishCount), forState: .Normal)
        btn1.setTitleColor(UIColor.blackColor(), forState: .Normal)
        btn1.frame = CGRectMake(0, 0, 30, 25)
        btn1.addTarget(self, action: #selector(DetailDepartmentViewController.openWishList), forControlEvents: .TouchUpInside)
        self.navigationItem.setRightBarButtonItem(UIBarButtonItem(customView: btn1), animated: true);
        //Genera el boton del centro que contiene los puntos del usuario
        let button =  UIButton(type: .Custom)
        button.frame = CGRectMake(0, 0, 100, 40) as CGRect
        button.setTitle(String(points), forState: UIControlState.Normal)
        button.addTarget(self, action: #selector(DetailDepartmentViewController.clickOnButton(_:)), forControlEvents: UIControlEvents.TouchUpInside)
        self.navigationItem.titleView = button
        //Observer para actualizar la tabla
        NSNotificationCenter.defaultCenter().addObserver(self, selector: #selector(DetailDepartmentViewController.loadList(_:)),name:"loadDepartment", object: nil)
        //Observer para abrir scan
        NSNotificationCenter.defaultCenter().addObserver(self, selector: #selector(DetailDepartmentViewController.scan),name:"scan", object: nil)
        //Refreca la cantidad de items en la lista de deseos
        NSNotificationCenter.defaultCenter().addObserver(self, selector: #selector(DetailDepartmentViewController.refreshWishCount),name:"refreshWishCountDepartment", object: nil)
    }
    
    func refreshWishCount(){
        //Refresca el contador
        self.wishCount = defaults.objectForKey("wishCount")as!Int
        btn1.setTitle(String(self.wishCount), forState: .Normal)
    }
    
    func loadList(notification: NSNotification){
        //load data here
        self.collection.reloadData()
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
        return self.department.products.count
    }
    
    func scan(){
        let secondViewController = self.storyboard?.instantiateViewControllerWithIdentifier("ScanViewController") as! ScanViewController
        self.navigationController?.pushViewController(secondViewController, animated: true)

    }
    
    func collectionView(collectionView: UICollectionView, cellForItemAtIndexPath indexPath: NSIndexPath) -> UICollectionViewCell {
        //5
         let cell = collectionView.dequeueReusableCellWithReuseIdentifier(reuseIdentifier, forIndexPath: indexPath) as! DetailProductDepartmentCell
        
        let productObject = self.department.products[indexPath.row]
        
       cell.configure(productObject.productNamePropeties, urlImageProduct: productObject.imageUrlListPropeties[0],product: productObject)
        return cell
    }
    
    
    func collectionView(collectionView: UICollectionView, didSelectItemAtIndexPath indexPath: NSIndexPath) {
        self.actualyArrayIndex = indexPath.row
        let secondViewController = self.storyboard?.instantiateViewControllerWithIdentifier("DetailProductViewController") as! DetailProductViewController
        secondViewController.product = self.department.productsPropeties[self.actualyArrayIndex]
        self.navigationController?.pushViewController(secondViewController, animated: true)
        }

}
