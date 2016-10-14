//
//  Constants.swift
//  BeakonIOS
//
//  Created by Eduardo Trejos on 10/14/16.
//  Copyright © 2016 CentauroSolutions. All rights reserved.
//

import Foundation


struct Constants
{
    struct ws_response_code
    {
        static let not_found = "404"
        static let unauthorized = "401"
        static let server_error = "500"
        static let ok = "200"
        static let created = "201"
    }
    struct ws_services
    {
        static let profile = ""
        static let device = "http://bdevicedevel.cfapps.io/device/UID/"
    }
    struct general
    {
        static let kontakt_api_key = "ZtLtzUwyFjUFGlwjSxHoKsDKmyqjXNLc"
        static let content_type_json = "application/json"
        static let header_accept = "application/vnd.com.kontakt+json;version=8"
    }
    struct info_messages
    {
        static let beacon_not_belongs_to_promo = "El Beacon no esta asociado a ninguna promocion"
    }
    struct error_messages
    {
        static let get_beacon_info = "Problema al obtner datos del beacon"
        static let call_to_ws = "Hubo un error realizando la peticion: %@"
    }
}