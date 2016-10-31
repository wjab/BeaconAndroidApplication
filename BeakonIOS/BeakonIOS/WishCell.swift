//
//  WishCell.swift
//  BeakonIOS
//
//  Created by Alejandra on 9/12/16.
//  Copyright © 2016 CentauroSolutions. All rights reserved.
//

import UIKit
import Alamofire
import SwiftyJSON
import JLToast

class WishCell: UITableViewCell {
    @IBOutlet weak var priceL: UILabel!
    @IBOutlet weak var productImage: UIImageView!
    @IBOutlet weak var nameL: UILabel!
    @IBOutlet weak var deleteProduct: UIButton!
    var wishProduct:Wish = Wish()
    let utils = UtilsC()
    let defaults = NSUserDefaults.standardUserDefaults()
    
    internal func configure(name: String, urlImage: String,product: Wish) {
        nameL.text = product.productNamePropeties
        wishProduct = product
        let url = NSURL(string: urlImage)
        productImage.hnk_setImageFromURL(url!, placeholder: nil, success: { (image) -> Void in
            self.productImage.image = image
            }, failure: { (error) -> Void in
                self.productImage.image = UIImage(named: "image_not_found")
                
        })
        
        productImage.hnk_setImageFromURL(url!)
        priceL.text = "¢"+String(wishProduct.pricePropeties)
         deleteProduct.addTarget(self, action: #selector(deleteProductWish), forControlEvents: .TouchUpInside)
    }
    
    func deleteProductWish(){
        //user/wishlist/delete
        let url : String = "http://buserdevel.cfapps.io/user/wishlist/delete"
        let idUser = (defaults.objectForKey("userId") as? String)!
        let name = wishProduct.productNamePropeties
        let id = wishProduct.productIdPropeties
        let price = wishProduct.pricePropeties
        let urlImage = wishProduct.imageUrlListPropeties
        let newTodo = [
            "userId": idUser,
            "productName": name,
            "productId": id,
            "price": price,
            "imageUrlList":urlImage,
            "pointsByPrice": 0]
 
        Alamofire.request(.POST, url, parameters: newTodo as? [String : AnyObject], encoding: .JSON)
            .responseJSON
            {
                response in switch response.result
                {
                //Si la respuesta es satisfactoria
                case .Success(let JSON):
                    let response = JSON as! NSDictionary
                    //Si la respuesta no tiene status 404
                    if((response)["status"] as! Int != 404)
                    {
                        print("Genial")
                        NSNotificationCenter.defaultCenter().postNotificationName("loadDepartment", object: nil)
                        NSNotificationCenter.defaultCenter().postNotificationName("load", object: nil)
                        NSNotificationCenter.defaultCenter().postNotificationName("wish", object: nil)
                       JLToast.makeText("Eliminado correctamente").show()
                       self.utils.refreshDatas()
                    }
                    else
                    {
                        print("ERROR")
                    }
                case .Failure(let error):
                    print("Hubo un error realizando la peticion: \(error)")
                }
        }
    }
    
}
