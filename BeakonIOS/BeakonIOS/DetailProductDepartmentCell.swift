//
//  DetailProductDepartmentCell.swift
//  BeakonIOS
//
//  Created by Alejandra on 9/14/16.
//  Copyright © 2016 CentauroSolutions. All rights reserved.
//

import UIKit
import Alamofire
import SwiftyJSON

class DetailProductDepartmentCell: UICollectionViewCell {
    
    @IBOutlet weak var allowScan: UIButton!
    @IBOutlet weak var isAddedImage: UIButton!
    @IBOutlet weak var productImage: UIImageView!
    @IBOutlet weak var points: UILabel!
    @IBOutlet weak var price: UILabel!
    
    var product:Product = Product()
    let utilsC = UtilsC()
    let defaults = NSUserDefaults.standardUserDefaults()
    var wishArray = [Wish]()
    
    internal func configure(name: String, urlImageProduct: String, product: Product)
    {
        if(product.allowScanPropeties == false)
        {
            allowScan.hidden = true
        }
       
        self.product = product
        let url = NSURL(string: urlImageProduct)
        
        productImage.hnk_setImageFromURL(url!, placeholder: nil, success: { (image) -> Void in
            self.productImage.image = image
            }, failure: { (error) -> Void in
                self.productImage.image = UIImage(named: "image_not_found")
                
        })

    //Gradient
        let gradientLayerView: UIView = UIView(frame: CGRectMake(0, 0, productImage.bounds.width, productImage.bounds.height))
        let gradient: CAGradientLayer = CAGradientLayer()
        gradient.frame = gradientLayerView.bounds
        gradient.colors = [UIColor.clearColor().CGColor,UIColor.clearColor().CGColor,UIColor.grayColor().CGColor]
        gradientLayerView.layer.insertSublayer(gradient, atIndex: 0)
        self.productImage.layer.insertSublayer(gradientLayerView.layer, atIndex: 0)
        self.price.text = "¢"+String(product.pricePropeties)
        self.points.text = String(product.pointsByPricePropeties)
         //Accion del boton scanear
         allowScan.addTarget(self, action: #selector(scan), forControlEvents: .TouchUpInside)
        //Accion del boton añadir
        isAddedImage.addTarget(self, action: #selector(send), forControlEvents: .TouchUpInside)
        //Obtener la lista de deseos
        self.obtainWishListUser()
    }
    
    func scan()
    {
        NSNotificationCenter.defaultCenter().postNotificationName("scan", object: nil)
    }
    
    func send()
    {
        utilsC.addWishList(self.product)
        self.product.isAddedPropeties = true
        compare()
    }
    
    func obtainWishListUser()
    {
        let idUser = (defaults.objectForKey("userId") as? String)!
        //Endpoint
        let url : String = "http://buserdevel.cfapps.io/user/id/"+idUser
        Alamofire.request(.GET, url, encoding: .JSON)
            .responseJSON
            {
                response in switch response.result
                {
                //Si la respuesta es satisfactoria
                case .Success(let JSON):
                    let response = JSON as! NSDictionary
                    var user = JSON as! NSDictionary
                    //Si la respuesta no tiene status 404
                    if((response)["status"] as! Int != 404)
                    {
                        user = response.objectForKey("user")! as! NSDictionary
                        let productList = user.mutableArrayValueForKey("productWishList")
                        
                        for (_, product) in productList.enumerate()
                        {
                            let wishObject = Wish()
                            wishObject.productIdPropeties = product.objectForKey("productId") as! String
                            wishObject.productNamePropeties = product.objectForKey("productName") as! String
                            wishObject.pricePropeties = product.objectForKey("price") as! Int
                            wishObject.imageUrlListPropeties = product.objectForKey("imageUrlList") as! String
                            wishObject.pointsByPricePropeties = product.objectForKey("pointsByPrice") as! Int
                            
                            let idWish = wishObject.productIdPropeties
                            let idProduct = self.product.productIdPropeties
                            if(idWish == idProduct){
                                self.product.isAddedPropeties = true
                            }
                            self.wishArray.append(wishObject)
                        }
                        self.compare()
                    }
                    else
                    {
                        print("Hubo un error obteniendo los datos de lista de deseos")
                    }
                case .Failure(let error):
                    print("Hubo un error realizando la peticion: \(error)")
                }
        }
    }
    
    func compare(){
                if(self.product.isAddedPropeties == true){
                    self.isAddedImage.setImage(UIImage(named: "icon_added"), forState: UIControlState.Normal)
                }
                else
                {
                    self.isAddedImage.setImage(UIImage(named: "icon_add"), forState: UIControlState.Normal)
                }
        }

}
