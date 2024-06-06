import React from "react";

const SaloonItem = function(props){

    return( 
            <li class="list-group-item d-flex justify-content-between align-items-start">
                <div class="ms-2 me-auto">
                <div class="fw-bold"><a href={'/saloons/'+props.saloon.id} style={{textDecoration: 0}}>{props.saloon.name}</a></div>
                {props.saloon.region}, {props.saloon.city}, {props.saloon.address}
                </div>
            </li>
    )
}

export default SaloonItem;