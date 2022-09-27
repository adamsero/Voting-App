import { HttpErrorResponse } from '@angular/common/http';
import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { VoterData } from 'src/app/datatypes/voter-data';
import { VotingService } from 'src/app/services/voting.service';
import { GridComponent } from '../grid/grid.component';

@Component({
  selector: 'app-voter-table',
  templateUrl: './voter-table.component.html',
})
export class VoterTableComponent implements OnInit {
  columnDefs = [
    { field: 'name', headerName: 'Voter name' },
    {
      field: 'votedFor',
      headerName: 'Voted for',
      valueFormatter: GridComponent.nullStringGridFormatter,
    },
  ];
  rowData: Array<VoterData> = null!;

  @Output() onNewVoter = new EventEmitter();

  constructor(
    private votingService: VotingService,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    this.refreshVoters();
  }

  createVoter(name: string): void {
    this.votingService.createVoter(name).subscribe({
      next: (response: string) => {
        this.toastr.success(response, 'Success');
        this.refreshVoters();
        this.onNewVoter.emit();
      },
      error: (errorResponse: HttpErrorResponse) =>
        this.toastr.error(errorResponse.error, 'Error'),
    });
  }

  refreshVoters(): void {
    this.votingService.getVoters().subscribe((data) => {
      this.rowData = data;
    });
  }
}
