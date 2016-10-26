//
//  DetailProductViewController.swift
//  BeakonIOS
//
//  Created by Alejandra on 9/9/16.
//  Copyright © 2016 CentauroSolutions. All rights reserved.
//

import UIKit
import Alamofire
import SwiftyJSON
import JLToast

class DetailProductViewController: UIViewController {
    var product:Product!
    @IBOutlet weak var name: UILabel!
    @IBOutlet weak var price: UILabel!
    @IBOutlet weak var details: UILabel!
    @IBOutlet weak var productImage: UIImageView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        navigationItem.backBarButtonItem = UIBarButtonItem(title: "", style: .Plain, target: nil, action: nil)
        print(product.imageUrlListPropeties[0])
        productImage.image = NSURL(string: String(product.imageUrlListPropeties[0])).flatMap { NSData(contentsOfURL: $0) }.flatMap { UIImage(data: $0) }!
        name.text = product.productNamePropeties
        details.text = product.detailsPropeties
        price.text = String(product.pricePropeties)
        let btn1 = UIButton()
        btn1.setImage(UIImage(named: "icon_add"), forState: .Normal)
        btn1.frame = CGRectMake(0, 0, 30, 25)
        btn1.addTarget(self, action: #selector(DetailProductViewController.addWishList), forControlEvents: .TouchUpInside)
        self.navigationItem.setRightBarButtonItem(UIBarButtonItem(customView: btn1), animated: true);

    }
    func addWishList()
    {
        let defaults = NSUserDefaults.standardUserDefaults()
        let url : String = "http://buserdevel.cfapps.io/user/wishlist/add"
        let idUser = (defaults.objectForKey("userId") as? String)!
        let name = product.productNamePropeties
        let id = product.productIdPropeties
        let price = product.pricePropeties
        let urlImage = product.imageUrlListPropeties[0]
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
                        if((response)["message"] as! String=="Product already added to wishlist"){
                            JLToast.makeText("Este producto ya se encuentra en la lista de deseos").show()
                        }
                        else{
                            print("Genial")
                            JLToast.makeText("Añadido correctamente").show()
                             NSNotificationCenter.defaultCenter().postNotificationName("loadDepartment", object: nil)
                             NSNotificationCenter.defaultCenter().postNotificationName("load", object: nil)
                        }
                        
                    }
                    else
                    {
                        print("ERROR")
                    }
                case .Failure(let error):
                    print("Hubo un error realizando la peticion: \(error)")
                }
        }
        print(product.productNamePropeties)
    }

    override func didReceiveMemoryWarning() {
        
    }
}
