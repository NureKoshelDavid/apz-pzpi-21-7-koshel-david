import React from "react";

const StaffItem = function(props){

    return( 
            <li class="list-group-item d-flex justify-content-between align-items-start">
                <div class="ms-2 me-auto">
                <div class="fw-bold"><a href={'/users/'+props.staffItem.id} style={{textDecoration: 0}}>{props.staffItem.name} {props.staffItem.surname} {props.staffItem.patronymic}</a></div>
                {props.staffItem.phone}
                </div>
            </li>
    )
}

export default StaffItem;