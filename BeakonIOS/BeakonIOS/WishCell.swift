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
    let defaults = NSUserDefaults.standardUserDefaults()
    
    internal func configure(name: String, urlImage: String,product: Wish) {
        nameL.text = product.productNamePropeties
        wishProduct = product
        let url = NSURL(string: urlImage)
        productImage.hnk_setImageFromURL(url!)
        priceL.text = String(wishProduct.pricePropeties)
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
                       JLToast.makeText("Eliminado correctamente").show()
                        NSNotificationCenter.defaultCenter().postNotificationName("wish", object: nil)
                        NSNotificationCenter.defaultCenter().postNotificationName("refreshWishCount", object: nil)
                        NSNotificationCenter.defaultCenter().postNotificationName("refreshWishCountHome", object: nil)
                        NSNotificationCenter.defaultCenter().postNotificationName("refreshWishCountFaq", object: nil)
                        NSNotificationCenter.defaultCenter().postNotificationName("refreshWishCountDetailPromo", object: nil)
                        NSNotificationCenter.defaultCenter().postNotificationName("refreshWishCountNotification", object: nil)
                        NSNotificationCenter.defaultCenter().postNotificationName("refreshWishCountDetailCategory", object: nil)
                        NSNotificationCenter.defaultCenter().postNotificationName("refreshWishCountDetailShop", object: nil)
                        NSNotificationCenter.defaultCenter().postNotificationName("refreshWishCountProfile", object: nil)
                        NSNotificationCenter.defaultCenter().postNotificationName("refreshWishCountPoints", object: nil)
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
