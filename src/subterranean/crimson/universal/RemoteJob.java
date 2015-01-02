package subterranean.crimson.universal;

/*
 * 	Crimson Extended Administration Tool
 *  Copyright (C) 2015 Subterranean Security
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

public class RemoteJob implements Job {

	@Override
	public String getJobCompletion() {
		return "Test";
	}

	@Override
	public String getJobName() {
		return "Test";
	}

	@Override
	public int getJobNumber() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getJobStatus() {
		return "Test";
	}

	@Override
	public String getJobType() {
		return "Test";
	}

	@Override
	public void pauseJob() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resumeJob() {
		// TODO Auto-generated method stub

	}

	@Override
	public void startJob() {
		// TODO Auto-generated method stub

	}

	@Override
	public void terminateJob() {
		// TODO Auto-generated method stub

	}

}
