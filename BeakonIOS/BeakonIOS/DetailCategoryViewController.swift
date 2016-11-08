//
//  DetailCategoryViewController.swift
//  BeakonIOS
//
//  Created by Alejandra on 9/9/16.
//  Copyright © 2016 CentauroSolutions. All rights reserved.
//

import UIKit
import Alamofire
import SwiftyJSON

class DetailCategoryViewController: UIViewController, UICollectionViewDataSource, UICollectionViewDelegate
{
    var product : [Product] = []
    var image:String = ""
    var actualyArrayIndex = 0
    var categoryName:String = ""
    private let reuseIdentifier = "cellProductCategory"
    @IBOutlet weak var collection: UICollectionView!
    @IBOutlet weak var imageCategory: UIImageView!
    var wishCount = 1
     let defaults = NSUserDefaults.standardUserDefaults()
     var btn1 = UIButton()
    
    override func viewDidLoad()
    {
        super.viewDidLoad()
        self.navigationItem.title = ""
        
        service()
        let gradientLayerView: UIView = UIView(frame: CGRectMake(0, 0, imageCategory.bounds.width, imageCategory.bounds.height))
        let gradient: CAGradientLayer = CAGradientLayer()
        gradient.frame = gradientLayerView.bounds
        gradient.colors = [
            UIColor.clearColor().CGColor,
            UIColor.clearColor().CGColor,
            UIColor.grayColor().CGColor
        ]
        gradientLayerView.layer.insertSublayer(gradient, atIndex: 0)
        self.imageCategory.layer.insertSublayer(gradientLayerView.layer, atIndex: 0)
        
        let urlCategoryImage = NSURL(string: String(image))
        imageCategory.hnk_setImageFromURL(urlCategoryImage!, placeholder: nil, success: { (image) -> Void in
            self.imageCategory.image = image
            }, failure: { (error) -> Void in
                self.imageCategory.image = UIImage(named: "image_not_found")
                
        })

        //Observer para actualizar la tabla
        NSNotificationCenter.defaultCenter().addObserver(self, selector: #selector(DetailCategoryViewController.loadList),name:"load", object: nil)
        
        //Carga los datos de user defaults
        wishCount = defaults.objectForKey("wishCount")as!Int
        let points = defaults.objectForKey("points") as! Int
        
        //Boton para abir la lista de deseos
        btn1 = Utils.loadWishListButton(btn1, wishCount: wishCount)
        btn1.addTarget(self, action: #selector(DetailCategoryViewController.openWishList), forControlEvents: .TouchUpInside)
        self.navigationItem.setRightBarButtonItem(UIBarButtonItem(customView: btn1), animated: true);
        
        // Crea el view con el label de puntos y el arrow de imagen
        let myView = Utils.createPointsView(points, activateEvents: true)
        let gesture = UITapGestureRecognizer(target : self, action: #selector(DetailCategoryViewController.clickOnButton))
        myView.addGestureRecognizer(gesture)
        
        self.navigationItem.titleView = myView
        
        NSNotificationCenter.defaultCenter().addObserver(self, selector: #selector(DetailCategoryViewController.refreshWishCount),name:"refreshWishCountDetailCategory", object: nil)
    }
    
    func refreshWishCount(){
        //Refresca el contador
        self.wishCount = defaults.objectForKey("wishCount")as!Int
        btn1.setTitle(String(self.wishCount), forState: .Normal)
    }
    
    //Abre el historial de puntos
    func clickOnButton() {
        let secondViewController = self.storyboard?.instantiateViewControllerWithIdentifier("HistoryPointsViewController") as! HistoryPointsViewController
        self.navigationController?.pushViewController(secondViewController, animated: true)
    }
    //Abre la lista de deseos
    func openWishList(){
        let secondViewController = self.storyboard?.instantiateViewControllerWithIdentifier("WishViewController") as! WishViewController
        self.navigationController?.pushViewController(secondViewController, animated: true)
    }

    func loadList(){
        self.collection.reloadData()
    }
    
    func service(){
        //Endpoint
        let url : String =  Constants.ws_services.productsCategory+self.categoryName
        
        //Crea el request
        Alamofire.request(.GET, url, encoding: .JSON)
            .responseJSON
            {
                response in switch response.result
                {
                //Si la respuesta es satisfactoria
                case .Success(let JSON):
                    let response = JSON as! NSDictionary
                    //Si la respuesta no tiene status 404
                    if((response)["status"] as! String == Constants.ws_response_code.ok)
                    {
                        let productList = response.mutableArrayValueForKey("merchantProfile")
                        for (_, element) in productList.enumerate() {
                            let productObject = Product()
                            productObject .detailsPropeties = element.objectForKey("details") as! String
                            
                            if(element.objectForKey("allowScan")is NSNull){productObject .allowScanPropeties = false}
                            else{productObject .allowScanPropeties =  true}
                            if(element.objectForKey("code")is NSNull){productObject .codePropeties = ""}
                            else{productObject .codePropeties = element.objectForKey("code") as! String}
                            productObject .imageUrlListPropeties = element.objectForKey("imageUrlList") as! Array<String>
                            productObject .pointsByPricePropeties = element.objectForKey("pointsByPrice") as! Int
                            productObject .pointsByScanPropeties = element.objectForKey("pointsByScan") as! Int
                            productObject .pricePropeties = element.objectForKey("price") as! Int
                            productObject .productIdPropeties = element.objectForKey("productId") as! String
                            productObject .productNamePropeties = element.objectForKey("productName") as! String
                            self.product.append(productObject )
                        }
                        self.collection.reloadData()
                    }
                    else
                    {
                        print(Constants.error_messages.error_category, self.categoryName)
                    }
                case .Failure(let error):
                    print("Hubo un error realizando la peticion: \(error)")
                }
        }
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    func numberOfSectionsInCollectionView(collectionView: UICollectionView) -> Int {
        return 1
    }
    
    func collectionView(collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return self.product.count
    }
    
    //Carga cada celda
    func collectionView(collectionView: UICollectionView, cellForItemAtIndexPath indexPath: NSIndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCellWithReuseIdentifier(reuseIdentifier, forIndexPath: indexPath) as! DetailProductCell
        let productObject = self.product[indexPath.row]
        let image = productObject.imageUrlListPropeties[0]
        cell.configure(productObject.productNamePropeties,urlImageProduct: image, product: productObject)
       return cell
    }
    
    func send(){
        print(product[self.actualyArrayIndex].productNamePropeties)
    }
    
    //Envia los datos al segundo ViewController
    func collectionView(collectionView: UICollectionView, didSelectItemAtIndexPath indexPath: NSIndexPath) {
        self.actualyArrayIndex = indexPath.row
        let cell = collectionView.dequeueReusableCellWithReuseIdentifier(reuseIdentifier, forIndexPath: indexPath) as! DetailProductCell
        cell.isAddedImage.addTarget(self, action: #selector(send), forControlEvents: .TouchUpInside)
        let secondViewController = self.storyboard?.instantiateViewControllerWithIdentifier("DetailProductViewController") as! DetailProductViewController
        secondViewController.product = self.product[self.actualyArrayIndex]
        self.navigationController?.pushViewController(secondViewController, animated: true)
    }

}
