import { HttpErrorResponse } from '@angular/common/http';
import {
  Component,
  ElementRef,
  EventEmitter,
  OnInit,
  Output,
  ViewChild,
} from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { VotingService } from 'src/app/services/voting.service';

@Component({
  selector: 'app-vote-select',
  templateUrl: './vote-select.component.html',
})
export class VoteSelectComponent implements OnInit {
  availableVoters: string[] = [];
  availableCandidates: string[] = [];

  @Output() onVoteCast = new EventEmitter();

  constructor(
    private votingService: VotingService,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    this.refreshVoters();
    this.refreshCandidates();
  }

  refreshVoters(): void {
    this.votingService
      .getAvailableVoterNames()
      .subscribe((data) => (this.availableVoters = data));
  }

  refreshCandidates(): void {
    this.votingService
      .getCandidateNames()
      .subscribe((data) => (this.availableCandidates = data));
  }

  vote(voterSelect: string, candidateSelect: string): void {
    this.votingService.vote(voterSelect, candidateSelect).subscribe({
      next: (response: string) => {
        this.toastr.success(response, 'Success');
        this.refreshAfterSuccessfulVote();
      },
      error: (errorResponse: HttpErrorResponse) =>
        this.toastr.error(errorResponse.error, 'Error'),
    });
  }

  refreshAfterSuccessfulVote(): void {
    this.refreshVoters();
    this.refreshCandidates();
    this.onVoteCast.emit();
  }
}
