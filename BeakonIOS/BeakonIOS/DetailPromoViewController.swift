//
//  DetailPromoViewController.swift
//  BeakonIOS
//
//  Created by Alejandra on 8/30/16.
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
    @IBOutlet weak var sharePromo: UIButton!
    //var branchUniversalObject: BranchUniversalObject = BranchUniversalObject(canonicalIdentifier: "")
    
    override func viewDidLoad()
    {
        //self.branchUniversalObject = BranchUniversalObject(canonicalIdentifier: toPass.idPropeties)
        super.viewDidLoad()
        self.service()
        //branchUniversalObject.userCompletedAction(BNCRegisterViewEvent)
         sharePromo.addTarget(self, action: #selector(DetailPromoViewController.share), forControlEvents: .TouchUpInside)
        let defaults = NSUserDefaults.standardUserDefaults()
        let points = defaults.objectForKey("points") as! Int
        //Cambia el tamaño de los tabs
        //Genera el boton de la derecha que contiene el corazon que abre la lista de deseos
        let btn1 = UIButton()
        btn1.setImage(UIImage(named: "icon_added"), forState: .Normal)
        btn1.frame = CGRectMake(0, 0, 30, 25)
        btn1.addTarget(self, action: #selector(DetailPromoViewController.openWishList), forControlEvents: .TouchUpInside)
        self.navigationItem.setRightBarButtonItem(UIBarButtonItem(customView: btn1), animated: true);
        //Genera el boton del centro que contiene los puntos del usuario
        let button =  UIButton(type: .Custom)
        button.frame = CGRectMake(0, 0, 100, 40) as CGRect
        button.setTitle(String(points), forState: UIControlState.Normal)
        button.addTarget(self, action: #selector(DetailPromoViewController.clickOnButton(_:)), forControlEvents: UIControlEvents.TouchUpInside)
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
    
    func share(){
        let activityViewController = UIActivityViewController(activityItems: [toPass.descriptionPromoPropeties+" en: "+name as NSString, imagePromo ], applicationActivities: nil)
        presentViewController(activityViewController, animated: true, completion: {})
        
    }
    
    /*func branchUniversal(){
      
        
        branchUniversalObject.title = toPass.descriptionPromoPropeties
        branchUniversalObject.contentDescription = toPass.descriptionPromoPropeties
        branchUniversalObject.imageUrl = toPass.imagesPropeties
        
        branchUniversalObject.addMetadataKey("idPromo", value: toPass.idPropeties)
        branchUniversalObject.addMetadataKey("departmentId", value: toPass.departmentIdPropeties)
        branchUniversalObject.addMetadataKey("description", value: toPass.descriptionPromoPropeties)
        branchUniversalObject.addMetadataKey("idProduct", value: toPass.productIdPropeties)
        branchUniversalObject.addMetadataKey("idMerchant", value: toPass.merchantIdPropeties)
        branchUniversalObject.addMetadataKey("image", value: toPass.imagesPropeties)
        branchUniversalObject.addMetadataKey("points", value:  String( toPass.giftPointsPropeties))
        branchUniversalObject.addMetadataKey("titulo", value: toPass.titlePropeties)
        branchUniversalObject.addMetadataKey("product_picture", value: "product_picture")
        
        let linkProperties: BranchLinkProperties = BranchLinkProperties()
        linkProperties.feature = "sharing"
        linkProperties.channel = "facebook"
      
        branchUniversalObject.showShareSheetWithLinkProperties(linkProperties, andShareText: "Super amazing thing I want to share!", fromViewController: self)
        {
          (activityType, completed) in
               if (completed) {
                    print(String(format: "Completed sharing to %@", activityType!))
                }
               else
               {
                  print("Link sharing cancelled")
               }
        }
        
}
*/
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
