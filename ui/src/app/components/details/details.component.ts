import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";

// @Component({
//   selector: 'app-details',
//   templateUrl: './details.component.html',
//   styleUrls: ['./details.component.scss']
// })
// export class DetailsComponent implements OnInit {
//   todoItem: any;
//
//   constructor(public todosService: any, private route: ActivatedRoute) { }
//
//   ngOnInit(): void {
//     this.route.paramMap.subscribe(paramMap => {
//       if (paramMap.has("id"))
//         this.todosService.getOne(paramMap.get("id")).subscribe((data: any) => {
//           this.todoItem = data;
//         })
//     })
//   }
//
//   onSubmit() {
//     // @ts-ignore
//     this.todosService.update(this.todoItem._id.$oid, this.todoItem).subscribe((data: {}) => {
//       console.log(data);
//     })
//   }
// }
