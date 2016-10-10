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
    @IBOutlet weak var nameL: UILabel!
   @IBOutlet weak var isAddedImage: UIButton!
    @IBOutlet weak var productImage: UIImageView!
    var product:Product = Product()
    let utilsC = UtilsC()
    let defaults = NSUserDefaults.standardUserDefaults()
    var wishArray = [Wish]()
    
    internal func configure(name: String, urlImageProduct: String, product: Product) {
        nameL.text = name
        self.product = product
        productImage.image = NSURL(string: String(urlImageProduct)).flatMap { NSData(contentsOfURL: $0) }.flatMap { UIImage(data: $0) }!
        //Gradient
        let gradientLayerView: UIView = UIView(frame: CGRectMake(0, 0, productImage.bounds.width, productImage.bounds.height))
        let gradient: CAGradientLayer = CAGradientLayer()
        gradient.frame = gradientLayerView.bounds
        gradient.colors = [UIColor.clearColor().CGColor,UIColor.clearColor().CGColor,UIColor.grayColor().CGColor]
        gradientLayerView.layer.insertSublayer(gradient, atIndex: 0)
        self.productImage.layer.insertSublayer(gradientLayerView.layer, atIndex: 0)
        //Accion del boton añadir
        isAddedImage.addTarget(self, action: #selector(send), forControlEvents: .TouchUpInside)
        //Obtener la lista de deseos
        self.obtainWishListUser()
    }
    
    func send(){
        utilsC.addWishList(self.product)
        compare()
        NSNotificationCenter.defaultCenter().postNotificationName("loadDepartment", object: nil)
    }
    
    func obtainWishListUser(){
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
                        
                        for (indexP, product) in productList.enumerate()
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
                                //Si si es igual se actualiza la propiedad
                                self.product.isAddedPropeties = true
                                print("id" + idProduct)
                                print("addedPropetiesTrueee:  " + String(self.product.isAddedPropeties))
                                print("------------------------------------------------------------------------")
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
