//
//  DetailPromoViewController.swift
//  BeakonIOS
//
//  Created by Christopher on 8/30/16.
//  Copyright © 2016 CentauroSolutions. All rights reserved.
//

import UIKit
import Haneke
import Alamofire
import SwiftyJSON
class DetailPromoViewController: UIViewController {
    
    var toPass : Promo!
    var name : String! = "name"
    var image : String!
    var adress : String! = "adress"
    @IBOutlet weak var imagePromo: UIImageView!
    @IBOutlet weak var nameShop: UILabel!
    @IBOutlet weak var adressShop: UILabel!
    @IBOutlet weak var descriptionPromo: UILabel!
    @IBOutlet weak var imageShop: UIImageView!
    
    override func viewDidLoad()
    {
        super.viewDidLoad()
        self.service()
    }
    
    func charge()
    {
        let url = NSURL(string: String(toPass.imagesPropeties))!
        let urlShop = NSURL(string: String(image))!
        descriptionPromo.text = String(toPass.descriptionPromoPropeties)
        nameShop.text = name
        adressShop.text = adress
        imageShop.hnk_setImageFromURL(urlShop, format: Format<UIImage>(name: "original"))
        imagePromo.hnk_setImageFromURL(url, format: Format<UIImage>(name: "original"))
    }
    
    override func viewWillAppear(animated: Bool) {
        super.viewWillAppear(animated)
        print(String(toPass.descriptionPromoPropeties))
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func service()
    {
        //Endpoint
        var url : String = "http://bmerchantprofiledevel.cfapps.io/merchantprofile/"
        url += String(toPass.merchantIdPropeties)
        //Crea el request
        Alamofire.request(.GET, url, encoding: .JSON)
            .responseJSON
            {
                response in switch response.result
                {
                //Si la respuesta es satisfactoria
                case .Success(let JSON):
                    let response = JSON as! NSDictionary
                    var shop = JSON as! NSDictionary
                    //Si la respuesta no tiene status 404
                    if((response)["status"] as! String != "404")
                    {
                        shop = response.objectForKey("merchantProfile")! as! NSDictionary
                        //obtiene la contraseña
                        self.adress = (shop)["address"] as! String
                        self.image = (shop)["image"] as! String
                        self.name = (shop)["merchantName"] as! String
                    
                    }
                    else
                    {
                        print("Problema al obtner datos de la tienda")
                    }
                    self.charge()
                case .Failure(let error):
                    print("Hubo un error realizando la peticion: \(error)")
                }
        }
    }

}
