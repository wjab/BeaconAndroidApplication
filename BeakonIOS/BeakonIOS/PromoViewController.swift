//
//  PromoViewController.swift
//  BeakonIOS
//
//  Created by Alejandra on 8/29/16.
//  Copyright © 2016 CentauroSolutions. All rights reserved.
//

import UIKit
import Alamofire
import SwiftyJSON
import Haneke
class PromoViewController: UIViewController , UITableViewDelegate, UITableViewDataSource{
    var promoArray: [Promo] = []
    var actualyArrayIndex = 0
    @IBOutlet weak var table: UITableView!
    let cellReuseIdentifier = "cellPromo"
    
    override func viewDidLoad() {
        super.viewDidLoad()
        service()
        table.delegate = self
        table.dataSource = self
        
    }
    
    func service(){
        //Endpoint
        let url : String = "http://bpromodevel.cfapps.io/promo"
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
                    if((response)["status"] as! Int != 404)
                    {
                        let promoList = response.mutableArrayValueForKey("listPromo")
                        for (_, element) in promoList.enumerate() {
                            //print(index, ":", element)
                            let promoObject = Promo()
                            promoObject.codePropeties = element.objectForKey("code") as! String
                            promoObject.descriptionPromoPropeties = element.objectForKey("description") as! String
                            promoObject.enablePropeties = element.objectForKey("enable") as! Bool
                            promoObject.giftPointsPropeties = element.objectForKey("giftPoints") as! Int
                            promoObject.merchantIdPropeties = element.objectForKey("merchantId") as! String
                            promoObject.titlePropeties = element.objectForKey("title") as! String
                            promoObject.typePropeties = element.objectForKey("type") as! String
                            promoObject.imagesPropeties = element.objectForKey("images") as! String
                            self.promoArray.append(promoObject)
                            print(String(promoObject.codePropeties))
                        }
                        self.table.reloadData()
                    }
                    else
                    {
                        print("Hubo un error obteniendo los datos de promociones")
                    }
                case .Failure(let error):
                    print("Hubo un error realizando la peticion: \(error)")
                }
        }
    }
    
    func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.promoArray.count
    }
    
    func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell:PromoCell = self.table.dequeueReusableCellWithIdentifier(cellReuseIdentifier) as! PromoCell!
        let promoObject = self.promoArray[indexPath.row]
        let name = promoObject.descriptionPromoPropeties
        let urlImage = promoObject.imagesPropeties
         cell.configure(name,urlImagePromo: urlImage)
        return cell
    }
    
    func tableView(tableView: UITableView, didSelectRowAtIndexPath indexPath: NSIndexPath) {
        self.actualyArrayIndex = indexPath.row
        let secondViewController = self.storyboard?.instantiateViewControllerWithIdentifier("DetailPromoViewController") as! DetailPromoViewController
        secondViewController.toPass = self.promoArray[self.actualyArrayIndex]
        self.navigationController?.pushViewController(secondViewController, animated: true)
        
        
    }
    
    
}