import { Component, Input, OnInit } from '@angular/core';
import { GridReadyEvent } from 'ag-grid-community';

@Component({
  selector: 'app-grid',
  templateUrl: './grid.component.html',
})
export class GridComponent implements OnInit {
  @Input() rowData: any;
  @Input() columnDefs: any;

  constructor() {}

  ngOnInit(): void {}

  onGridReady(params: GridReadyEvent) {
    window.addEventListener('resize', function () {
      setTimeout(function () {
        params.api.sizeColumnsToFit();
      });
    });
    params.api.sizeColumnsToFit();
  }

  static nullStringGridFormatter(data: any) {
    if (!data.value) {
      return '---';
    }
    return data.value;
  }

  static numberFormatter(data: any): string {
    return data.value?.toString();
  }
}
